package com.smartaccounting.core.presentation.ui.bill

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.smartaccounting.core.domain.model.Bill
import com.smartaccounting.core.presentation.ui.theme.Expense
import com.smartaccounting.core.presentation.ui.theme.Income
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBillScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddBillViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateBack()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("添加账单") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FilterChip(
                    selected = uiState.type == Bill.TYPE_EXPENSE,
                    onClick = { viewModel.updateType(Bill.TYPE_EXPENSE) },
                    label = { Text("支出") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = uiState.type == Bill.TYPE_INCOME,
                    onClick = { viewModel.updateType(Bill.TYPE_INCOME) },
                    label = { Text("收入") },
                    modifier = Modifier.weight(1f)
                )
            }
            
            OutlinedTextField(
                value = uiState.amount,
                onValueChange = { viewModel.updateAmount(it) },
                label = { Text("金额") },
                leadingIcon = { Text("¥") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = uiState.merchantName,
                onValueChange = { viewModel.updateMerchantName(it) },
                label = { Text("商户名称") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            
            Button(
                onClick = { viewModel.addBill("user_id") },
                enabled = uiState.amount.isNotEmpty() && !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("保存")
            }
        }
    }
}
