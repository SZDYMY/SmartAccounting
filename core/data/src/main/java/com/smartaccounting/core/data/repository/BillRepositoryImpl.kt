package com.smartaccounting.core.data.repository

import com.smartaccounting.core.data.local.dao.BillDao
import com.smartaccounting.core.data.local.entity.BillEntity
import com.smartaccounting.core.domain.model.Bill
import com.smartaccounting.core.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class BillRepositoryImpl(
    private val billDao: BillDao
) : BillRepository {
    
    override fun getBillsByUserId(userId: String): Flow<List<Bill>> {
        return billDao.getBillsByUserId(userId).map { list ->
            list.map { it.toDomain() }
        }
    }
    
    override fun getBillsByDateRange(userId: String, startTime: Long, endTime: Long): Flow<List<Bill>> {
        return billDao.getBillsByDateRange(userId, startTime, endTime).map { list ->
            list.map { it.toDomain() }
        }
    }
    
    override suspend fun getBillById(billId: String): Bill? {
        return billDao.getBillById(billId)?.toDomain()
    }
    
    override fun searchBills(userId: String, keyword: String): Flow<List<Bill>> {
        return billDao.searchBills(userId, keyword).map { list ->
            list.map { it.toDomain() }
        }
    }
    
    override fun getBillsByTag(userId: String, tagId: String): Flow<List<Bill>> {
        return billDao.getBillsByTag(userId, tagId).map { list ->
            list.map { it.toDomain() }
        }
    }
    
    override suspend fun getTotalExpense(userId: String, startTime: Long, endTime: Long): Double {
        return billDao.getTotalAmountByType(userId, 0, startTime, endTime) ?: 0.0
    }
    
    override suspend fun getTotalIncome(userId: String, startTime: Long, endTime: Long): Double {
        return billDao.getTotalAmountByType(userId, 1, startTime, endTime) ?: 0.0
    }
    
    override suspend fun addBill(bill: Bill): String {
        val id = bill.id.ifEmpty { UUID.randomUUID().toString() }
        billDao.insertBill(bill.copy(id = id).toEntity())
        return id
    }
    
    override suspend fun updateBill(bill: Bill) {
        billDao.updateBill(bill.toEntity())
    }
    
    override suspend fun deleteBill(billId: String) {
        billDao.deleteBillById(billId)
    }
    
    override suspend fun deleteBills(billIds: List<String>) {
        billIds.forEach { billDao.deleteBillById(it) }
    }
    
    override suspend fun deleteAllBillsByUserId(userId: String) {
        billDao.deleteAllBillsByUserId(userId)
    }
    
    private fun BillEntity.toDomain() = Bill(
        id = id,
        userId = userId,
        amount = amount,
        type = type,
        tagId = tagId,
        merchantName = merchantName,
        paymentMethod = paymentMethod,
        note = note,
        transactionTime = transactionTime,
        isRecurring = isRecurring,
        recurringDays = recurringDays,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
    
    private fun Bill.toEntity() = BillEntity(
        id = id,
        userId = userId,
        amount = amount,
        type = type,
        tagId = tagId,
        merchantName = merchantName,
        paymentMethod = paymentMethod,
        note = note,
        transactionTime = transactionTime,
        isRecurring = isRecurring,
        recurringDays = recurringDays,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
