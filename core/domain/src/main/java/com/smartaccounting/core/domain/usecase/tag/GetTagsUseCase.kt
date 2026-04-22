package com.smartaccounting.core.domain.usecase.tag

import com.smartaccounting.core.domain.model.Tag
import com.smartaccounting.core.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow

class GetTagsUseCase(
    private val tagRepository: TagRepository
) {
    operator fun invoke(userId: String): Flow<List<Tag>> {
        return tagRepository.getAllTags(userId)
    }
    
    fun getParentTags(userId: String): Flow<List<Tag>> {
        return tagRepository.getParentTags(userId)
    }
    
    fun getChildTags(parentId: String): Flow<List<Tag>> {
        return tagRepository.getChildTags(parentId)
    }
}
