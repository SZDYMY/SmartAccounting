package com.smartaccounting.app.android.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class AlipayAccessibilityService : AccessibilityService() {

    companion object {
        const val TAG = "AlipayAccessibility"
        var instance: AlipayAccessibilityService? = null
    }


    private var lastPayResultTime: Long = 0
    private val payResultDebounce = 3000L

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED or
                        AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS or
                   AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS
            notificationTimeout = 100
        }
        serviceInfo = info
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event ?: return
        
        val packageName = event.packageName?.toString() ?: return
        if (packageName != "com.eg.android.AlipayGphone" && packageName != "com.alibaba.mobileib") {
            return
        }

        val currentTime = System.currentTimeMillis()
        if (currentTime - lastPayResultTime < payResultDebounce) {
            return
        }

        val className = event.className?.toString() ?: return
        
        if (className.contains("PayResultActivity") || className.contains("order")) {
            if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                extractPaymentInfo(event.source)
            }
        }
    }

    private fun extractPaymentInfo(rootNode: AccessibilityNodeInfo?) {
        rootNode ?: return
        
        try {
            var amount: Double? = null
            var merchantName: String? = null
            var transactionTime: Long = System.currentTimeMillis()
            
            val textContent = StringBuilder()
            rootNode.findAllInAccess { node ->
                node.text?.let { textContent.append(it).append(" ") }
                node.contentDescription?.let { textContent.append(it).append(" ") }
                false
            }
            
            val fullText = textContent.toString()
            
            val amountPattern = Regex("""¥?\s*(\d+\.?\d*)""")
            amountPattern.find(fullText)?.let { match ->
                amount = match.groupValues[1].toDoubleOrNull()
            }
            
            if (amount != null) {
                lastPayResultTime = System.currentTimeMillis()
                showFloatingWindow(amount!!, merchantName ?: "未知商户", "alipay", transactionTime)
            }
        } finally {
            rootNode.recycle()
        }
    }

    private fun showFloatingWindow(
        amount: Double,
        merchantName: String,
        paymentMethod: String,
        transactionTime: Long
    ) {
        val intent = Intent(this, FloatingWindowService::class.java).apply {
            putExtra("action", "show")
            putExtra("amount", amount)
            putExtra("merchantName", merchantName)
            putExtra("paymentMethod", paymentMethod)
            putExtra("transactionTime", transactionTime)
        }
        startService(intent)
    }

    override fun onInterrupt() {
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

    private fun AccessibilityNodeInfo.findAllInAccess(
        action: (AccessibilityNodeInfo) -> Boolean
    ) {
        if (action(this)) return
        for (i in 0 until childCount) {
            getChild(i)?.let { child ->
                try {
                    child.findAllInAccess(action)
                } finally {
                    child.recycle()
                }
            }
        }
    }
}
