package com.smartaccounting.core.domain.usecase.bill

import com.smartaccounting.core.domain.model.Bill
import com.smartaccounting.core.domain.repository.BillRepository
import kotlinx.coroutines.flow.Flow

class GetBillsUseCase(
    private val billRepository: BillRepository
) {
    operator fun invoke(userId: String): Flow<List<Bill>> {
        return billRepository.getBillsByUserId(userId)
    }
    
    fun byDateRange(userId: String, startTime: Long, endTime: Long): Flow<List<Bill>> {
        return billRepository.getBillsByDateRange(userId, startTime, endTime)
    }
    
    fun byTag(userId: String, tagId: String): Flow<List<Bill>> {
        return billRepository.getBillsByTag(userId, tagId)
    }
}
