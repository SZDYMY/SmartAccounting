package com.smartaccounting.core.domain.model

data class User(
    val id: String,
    val phone: String,
    val nickname: String,
    val avatar: String?,
    val email: String?,
    val createdAt: Long,
    val updatedAt: Long
)
