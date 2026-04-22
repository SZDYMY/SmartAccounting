package com.smartaccounting.core.presentation.ui.bill

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillDetailScreen(
    billId: String,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: () -> Unit,
    viewModel: BillListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val bill = uiState.bills.find { it.id == billId }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("账单详情") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "编辑")
                    }
                }
            )
        }
    ) { padding ->
        if (bill != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (bill.type == 0) "支出" else "收入",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "¥${String.format("%.2f", bill.amount)}",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                }
            }
        }
    }
}
