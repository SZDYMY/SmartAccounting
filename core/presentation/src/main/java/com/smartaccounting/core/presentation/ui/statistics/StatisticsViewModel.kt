package com.smartaccounting.core.presentation.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartaccounting.core.domain.model.Bill
import com.smartaccounting.core.domain.usecase.statistics.GetStatisticsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StatisticsUiState(
    val isLoading: Boolean = false,
    val totalExpense: Double = 0.0,
    val totalIncome: Double = 0.0,
    val expenseByTag: Map<String, Double> = emptyMap(),
    val dailyExpense: List<Pair<Long, Double>> = emptyList(),
    val dailyIncome: List<Pair<Long, Double>> = emptyList(),
    val bills: List<Bill> = emptyList(),
    val selectedYear: Int = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR),
    val selectedMonth: Int = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1,
    val error: String? = null
)

class StatisticsViewModel(
    private val getStatisticsUseCase: GetStatisticsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()
    
    fun loadStatistics(userId: String, year: Int, month: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                selectedYear = year,
                selectedMonth = month
            )
            try {
                val statistics = getStatisticsUseCase.getMonthlyStatistics(userId, year, month)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    totalExpense = statistics.totalExpense,
                    totalIncome = statistics.totalIncome,
                    expenseByTag = statistics.expenseByTag,
                    dailyExpense = statistics.dailyExpense.toList(),
                    dailyIncome = statistics.dailyIncome.toList()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    
    fun updateMonth(year: Int, month: Int) {
        _uiState.value = _uiState.value.copy(
            selectedYear = year,
            selectedMonth = month
        )
    }
}
