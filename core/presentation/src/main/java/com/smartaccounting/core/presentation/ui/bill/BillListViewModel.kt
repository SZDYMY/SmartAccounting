package com.smartaccounting.core.presentation.ui.bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartaccounting.core.domain.model.Bill
import com.smartaccounting.core.domain.usecase.bill.DeleteBillUseCase
import com.smartaccounting.core.domain.usecase.bill.GetBillsUseCase
import com.smartaccounting.core.domain.usecase.tag.GetTagsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class BillListUiState(
    val isLoading: Boolean = false,
    val bills: List<Bill> = emptyList(),
    val selectedBillIds: Set<String> = emptySet(),
    val isSelectionMode: Boolean = false,
    val error: String? = null
)

class BillListViewModel(
    private val getBillsUseCase: GetBillsUseCase,
    private val deleteBillUseCase: DeleteBillUseCase,
    private val getTagsUseCase: GetTagsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(BillListUiState())
    val uiState: StateFlow<BillListUiState> = _uiState.asStateFlow()
    
    fun loadBills(userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            getBillsUseCase(userId).collect { bills ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    bills = bills
                )
            }
        }
    }
    
    fun searchBills(userId: String, keyword: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            getBillsUseCase(userId).collect { bills ->
                val filtered = bills.filter {
                    it.merchantName?.contains(keyword, ignoreCase = true) == true ||
                    it.note?.contains(keyword, ignoreCase = true) == true
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    bills = filtered
                )
            }
        }
    }
    
    fun toggleSelection(billId: String) {
        val currentSelected = _uiState.value.selectedBillIds.toMutableSet()
        if (currentSelected.contains(billId)) {
            currentSelected.remove(billId)
        } else {
            currentSelected.add(billId)
        }
        _uiState.value = _uiState.value.copy(
            selectedBillIds = currentSelected,
            isSelectionMode = currentSelected.isNotEmpty()
        )
    }
    
    fun clearSelection() {
        _uiState.value = _uiState.value.copy(
            selectedBillIds = emptySet(),
            isSelectionMode = false
        )
    }
    
    fun deleteSelectedBills() {
        viewModelScope.launch {
            val ids = _uiState.value.selectedBillIds.toList()
            deleteBillUseCase.deleteMultiple(ids)
            clearSelection()
        }
    }
    
    fun deleteBill(billId: String) {
        viewModelScope.launch {
            deleteBillUseCase(billId)
        }
    }
}
