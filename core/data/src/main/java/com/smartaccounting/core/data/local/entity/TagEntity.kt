package com.smartaccounting.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey
    val id: String,
    val userId: String?,
    val name: String,
    val color: String,
    val icon: String?,
    val parentId: String?,
    val isSystem: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
