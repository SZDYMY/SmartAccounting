package com.smartaccounting.core.domain.repository

import com.smartaccounting.core.domain.model.Bill
import kotlinx.coroutines.flow.Flow

interface BillRepository {
    fun getBillsByUserId(userId: String): Flow<List<Bill>>
    fun getBillsByDateRange(userId: String, startTime: Long, endTime: Long): Flow<List<Bill>>
    suspend fun getBillById(billId: String): Bill?
    fun searchBills(userId: String, keyword: String): Flow<List<Bill>>
    fun getBillsByTag(userId: String, tagId: String): Flow<List<Bill>>
    suspend fun getTotalExpense(userId: String, startTime: Long, endTime: Long): Double
    suspend fun getTotalIncome(userId: String, startTime: Long, endTime: Long): Double
    suspend fun addBill(bill: Bill): String
    suspend fun updateBill(bill: Bill)
    suspend fun deleteBill(billId: String)
    suspend fun deleteBills(billIds: List<String>)
    suspend fun deleteAllBillsByUserId(userId: String)
}
