package com.smartaccounting.app.android.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import com.smartaccounting.app.MainActivity

class FloatingWindowService : Service() {

    private var windowManager: WindowManager? = null
    private var floatingView: View? = null
    private var isShowing = false

    private var pendingAmount: Double = 0.0
    private var pendingMerchantName: String = ""
    private var pendingPaymentMethod: String = ""
    private var pendingTransactionTime: Long = 0

    companion object {
        const val CHANNEL_ID = "floating_window_channel"
        const val NOTIFICATION_ID = 1001
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "show" -> {
                pendingAmount = intent.getDoubleExtra("amount", 0.0)
                pendingMerchantName = intent.getStringExtra("merchantName") ?: ""
                pendingPaymentMethod = intent.getStringExtra("paymentMethod") ?: ""
                pendingTransactionTime = intent.getLongExtra("transactionTime", System.currentTimeMillis())
                showFloatingWindow()
            }
            "hide" -> hideFloatingWindow()
            "confirm" -> {
                confirmTransaction()
                hideFloatingWindow()
            }
            "cancel" -> hideFloatingWindow()
        }
        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "记账服务",
            NotificationManager.IMPORTANCE_LOW
        ).apply { description = "用于后台保活" }
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun showFloatingWindow() {
        if (isShowing) hideFloatingWindow()
        startForeground(NOTIFICATION_ID, createNotification())
        
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            },
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply { gravity = Gravity.CENTER }

        floatingView = LayoutInflater.from(this).inflate(com.smartaccounting.app.R.layout.floating_window, null)
        floatingView?.findViewById<android.widget.TextView>(com.smartaccounting.app.R.id.tv_amount)?.text = "¥${String.format("%.2f", pendingAmount)}"
        floatingView?.findViewById<android.widget.TextView>(com.smartaccounting.app.R.id.tv_merchant)?.text = pendingMerchantName

        try {
            windowManager?.addView(floatingView, layoutParams)
            isShowing = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun hideFloatingWindow() {
        if (isShowing && floatingView != null) {
            try {
                windowManager?.removeView(floatingView)
            } catch (e: Exception) { e.printStackTrace() }
            floatingView = null
            isShowing = false
        }
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun confirmTransaction() {
        val intent = Intent(this, MainActivity::class.java).apply {
            action = "add_bill"
            putExtra("amount", pendingAmount)
            putExtra("merchantName", pendingMerchantName)
            putExtra("paymentMethod", pendingPaymentMethod)
            putExtra("transactionTime", pendingTransactionTime)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("智能记账宝")
            .setContentText("正在监听支付通知")
            .setSmallIcon(android.R.drawable.ic_menu_save)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    override fun onDestroy() {
        hideFloatingWindow()
        super.onDestroy()
    }
}
