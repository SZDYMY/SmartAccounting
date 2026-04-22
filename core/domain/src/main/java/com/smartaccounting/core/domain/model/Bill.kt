package com.smartaccounting.core.domain.model

data class Bill(
    val id: String = "",
    val userId: String,
    val amount: Double,
    val type: Int,
    val tagId: String? = null,
    val merchantName: String? = null,
    val paymentMethod: String? = null,
    val note: String? = null,
    val transactionTime: Long,
    val isRecurring: Boolean = false,
    val recurringDays: Int? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    companion object {
        const val TYPE_EXPENSE = 0
        const val TYPE_INCOME = 1
    }
}
