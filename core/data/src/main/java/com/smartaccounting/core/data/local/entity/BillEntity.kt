package com.smartaccounting.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bills")
data class BillEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val amount: Double,
    val type: Int,
    val tagId: String?,
    val merchantName: String?,
    val paymentMethod: String?,
    val note: String?,
    val transactionTime: Long,
    val isRecurring: Boolean,
    val recurringDays: Int?,
    val createdAt: Long,
    val updatedAt: Long
)
