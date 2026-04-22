package com.smartaccounting.core.data.local.dao

import androidx.room.*
import com.smartaccounting.core.data.local.entity.BillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {
    @Query("SELECT * FROM bills WHERE userId = :userId ORDER BY transactionTime DESC")
    fun getBillsByUserId(userId: String): Flow<List<BillEntity>>
    
    @Query("SELECT * FROM bills WHERE userId = :userId AND transactionTime BETWEEN :startTime AND :endTime ORDER BY transactionTime DESC")
    fun getBillsByDateRange(userId: String, startTime: Long, endTime: Long): Flow<List<BillEntity>>
    
    @Query("SELECT * FROM bills WHERE id = :billId")
    suspend fun getBillById(billId: String): BillEntity?
    
    @Query("SELECT * FROM bills WHERE userId = :userId AND (merchantName LIKE '%' || :keyword || '%' OR note LIKE '%' || :keyword || '%')")
    fun searchBills(userId: String, keyword: String): Flow<List<BillEntity>>
    
    @Query("SELECT * FROM bills WHERE userId = :userId AND tagId = :tagId ORDER BY transactionTime DESC")
    fun getBillsByTag(userId: String, tagId: String): Flow<List<BillEntity>>
    
    @Query("SELECT SUM(amount) FROM bills WHERE userId = :userId AND type = :type AND transactionTime BETWEEN :startTime AND :endTime")
    suspend fun getTotalAmountByType(userId: String, type: Int, startTime: Long, endTime: Long): Double?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill: BillEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBills(bills: List<BillEntity>)
    
    @Update
    suspend fun updateBill(bill: BillEntity)
    
    @Delete
    suspend fun deleteBill(bill: BillEntity)
    
    @Query("DELETE FROM bills WHERE id = :billId")
    suspend fun deleteBillById(billId: String)
    
    @Query("DELETE FROM bills WHERE userId = :userId")
    suspend fun deleteAllBillsByUserId(userId: String)
}
