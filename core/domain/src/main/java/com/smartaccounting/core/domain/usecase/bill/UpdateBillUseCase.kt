package com.smartaccounting.core.domain.usecase.bill

import com.smartaccounting.core.domain.model.Bill
import com.smartaccounting.core.domain.repository.BillRepository

class UpdateBillUseCase(
    private val billRepository: BillRepository
) {
    suspend operator fun invoke(bill: Bill): Result<Unit> {
        return try {
            billRepository.updateBill(bill.copy(updatedAt = System.currentTimeMillis()))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
