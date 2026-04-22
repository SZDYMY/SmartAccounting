package com.smartaccounting.core.presentation.ui.bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartaccounting.core.domain.model.Bill
import com.smartaccounting.core.domain.usecase.bill.AddBillUseCase
import com.smartaccounting.core.domain.usecase.tag.GetTagsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AddBillUiState(
    val isLoading: Boolean = false,
    val amount: String = "",
    val type: Int = Bill.TYPE_EXPENSE,
    val selectedTagId: String? = null,
    val merchantName: String = "",
    val paymentMethod: String = "",
    val note: String = "",
    val transactionTime: Long = System.currentTimeMillis(),
    val isRecurring: Boolean = false,
    val recurringDays: Int? = null,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class AddBillViewModel(
    private val addBillUseCase: AddBillUseCase,
    private val getTagsUseCase: GetTagsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddBillUiState())
    val uiState: StateFlow<AddBillUiState> = _uiState.asStateFlow()
    
    fun updateAmount(amount: String) {
        _uiState.value = _uiState.value.copy(amount = amount)
    }
    
    fun updateType(type: Int) {
        _uiState.value = _uiState.value.copy(type = type)
    }
    
    fun updateTag(tagId: String?) {
        _uiState.value = _uiState.value.copy(selectedTagId = tagId)
    }
    
    fun updateMerchantName(name: String) {
        _uiState.value = _uiState.value.copy(merchantName = name)
    }
    
    fun updatePaymentMethod(method: String) {
        _uiState.value = _uiState.value.copy(paymentMethod = method)
    }
    
    fun updateNote(note: String) {
        _uiState.value = _uiState.value.copy(note = note)
    }
    
    fun updateTransactionTime(time: Long) {
        _uiState.value = _uiState.value.copy(transactionTime = time)
    }
    
    fun updateRecurring(isRecurring: Boolean, days: Int? = null) {
        _uiState.value = _uiState.value.copy(isRecurring = isRecurring, recurringDays = days)
    }
    
    fun addBill(userId: String) {
        viewModelScope.launch {
            val state = _uiState.value
            val amount = state.amount.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                _uiState.value = state.copy(error = "请输入有效金额")
                return@launch
            }
            
            _uiState.value = state.copy(isLoading = true, error = null)
            
            val bill = Bill(
                userId = userId,
                amount = amount,
                type = state.type,
                tagId = state.selectedTagId,
                merchantName = state.merchantName.ifEmpty { null },
                paymentMethod = state.paymentMethod.ifEmpty { null },
                note = state.note.ifEmpty { null },
                transactionTime = state.transactionTime,
                isRecurring = state.isRecurring,
                recurringDays = state.recurringDays
            )
            
            addBillUseCase(bill)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
                }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
