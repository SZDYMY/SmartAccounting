package com.smartaccounting.core.domain.usecase.bill

import com.smartaccounting.core.domain.repository.BillRepository

class DeleteBillUseCase(
    private val billRepository: BillRepository
) {
    suspend operator fun invoke(billId: String): Result<Unit> {
        return try {
            billRepository.deleteBill(billId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteMultiple(billIds: List<String>): Result<Unit> {
        return try {
            billRepository.deleteBills(billIds)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
