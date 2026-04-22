package com.smartaccounting.core.domain.usecase.tag

import com.smartaccounting.core.domain.model.Tag
import com.smartaccounting.core.domain.repository.TagRepository

class ManageTagUseCase(
    private val tagRepository: TagRepository
) {
    suspend fun addTag(tag: Tag): Result<String> {
        return try {
            val id = tagRepository.addTag(tag)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateTag(tag: Tag): Result<Unit> {
        return try {
            tagRepository.updateTag(tag.copy(updatedAt = System.currentTimeMillis()))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteTag(tagId: String): Result<Unit> {
        return try {
            tagRepository.deleteTag(tagId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
