package com.smartaccounting.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.smartaccounting.core.presentation.navigation.AppNavHost
import com.smartaccounting.core.presentation.ui.theme.SmartAccountingTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        handleIntent(intent)
        
        setContent {
            SmartAccountingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleIntent(it) }
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == "add_bill") {
            val amount = intent.getDoubleExtra("amount", 0.0)
            val merchantName = intent.getStringExtra("merchantName") ?: ""
            val paymentMethod = intent.getStringExtra("paymentMethod") ?: ""
            val transactionTime = intent.getLongExtra("transactionTime", System.currentTimeMillis())
        }
    }
}
