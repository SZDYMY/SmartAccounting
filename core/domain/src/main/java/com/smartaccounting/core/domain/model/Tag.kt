package com.smartaccounting.core.domain.model

data class Tag(
    val id: String = "",
    val userId: String? = null,
    val name: String,
    val color: String,
    val icon: String? = null,
    val parentId: String? = null,
    val isSystem: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
