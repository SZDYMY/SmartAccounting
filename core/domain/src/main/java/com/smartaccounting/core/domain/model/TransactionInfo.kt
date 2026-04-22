package com.smartaccounting.core.domain.model

data class TransactionInfo(
    val amount: Double,
    val merchantName: String,
    val paymentMethod: String,
    val transactionTime: Long,
    val suggestedTagId: String? = null
)
