package com.smartaccounting.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val phone: String,
    val nickname: String,
    val avatar: String?,
    val email: String?,
    val passwordHash: String,
    val createdAt: Long,
    val updatedAt: Long
)
