package com.smartaccounting.core.domain.repository

import com.smartaccounting.core.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    fun getAllTags(userId: String): Flow<List<Tag>>
    fun getParentTags(userId: String): Flow<List<Tag>>
    fun getChildTags(parentId: String): Flow<List<Tag>>
    suspend fun getTagById(tagId: String): Tag?
    suspend fun addTag(tag: Tag): String
    suspend fun updateTag(tag: Tag)
    suspend fun deleteTag(tagId: String)
}
