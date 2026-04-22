package com.smartaccounting.core.domain.usecase.statistics

import com.smartaccounting.core.domain.model.Bill
import com.smartaccounting.core.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class Statistics(
    val totalExpense: Double,
    val totalIncome: Double,
    val expenseByTag: Map<String, Double>,
    val incomeByTag: Map<String, Double>,
    val dailyExpense: Map<Long, Double>,
    val dailyIncome: Map<Long, Double>
)

class GetStatisticsUseCase(
    private val billRepository: BillRepository
) {
    suspend fun getMonthlyStatistics(userId: String, year: Int, month: Int): Statistics {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(year, month - 1, 1, 0, 0, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)
        val startTime = calendar.timeInMillis
        
        calendar.add(java.util.Calendar.MONTH, 1)
        val endTime = calendar.timeInMillis - 1
        
        val totalExpense = billRepository.getTotalExpense(userId, startTime, endTime)
        val totalIncome = billRepository.getTotalIncome(userId, startTime, endTime)
        
        return Statistics(
            totalExpense = totalExpense,
            totalIncome = totalIncome,
            expenseByTag = emptyMap(),
            incomeByTag = emptyMap(),
            dailyExpense = emptyMap(),
            dailyIncome = emptyMap()
        )
    }
    
    fun getBillsByDateRange(userId: String, startTime: Long, endTime: Long): Flow<List<Bill>> {
        return billRepository.getBillsByDateRange(userId, startTime, endTime)
    }
}
