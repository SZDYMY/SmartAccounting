package com.smartaccounting.core.domain.usecase.bill

import com.smartaccounting.core.domain.model.Bill
import com.smartaccounting.core.domain.repository.BillRepository

class AddBillUseCase(
    private val billRepository: BillRepository
) {
    suspend operator fun invoke(bill: Bill): Result<String> {
        return try {
            val id = billRepository.addBill(bill)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
