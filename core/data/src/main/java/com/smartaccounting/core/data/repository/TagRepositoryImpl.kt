package com.smartaccounting.core.data.repository

import com.smartaccounting.core.data.local.dao.TagDao
import com.smartaccounting.core.data.local.entity.TagEntity
import com.smartaccounting.core.domain.model.Tag
import com.smartaccounting.core.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class TagRepositoryImpl(
    private val tagDao: TagDao
) : TagRepository {
    
    override fun getAllTags(userId: String): Flow<List<Tag>> {
        return tagDao.getAllTags(userId).map { list ->
            list.map { it.toDomain() }
        }
    }
    
    override fun getParentTags(userId: String): Flow<List<Tag>> {
        return tagDao.getParentTags(userId).map { list ->
            list.map { it.toDomain() }
        }
    }
    
    override fun getChildTags(parentId: String): Flow<List<Tag>> {
        return tagDao.getChildTags(parentId).map { list ->
            list.map { it.toDomain() }
        }
    }
    
    override suspend fun getTagById(tagId: String): Tag? {
        return tagDao.getTagById(tagId)?.toDomain()
    }
    
    override suspend fun addTag(tag: Tag): String {
        val id = tag.id.ifEmpty { UUID.randomUUID().toString() }
        tagDao.insertTag(tag.copy(id = id, isSystem = false).toEntity())
        return id
    }
    
    override suspend fun updateTag(tag: Tag) {
        tagDao.updateTag(tag.toEntity())
    }
    
    override suspend fun deleteTag(tagId: String) {
        tagDao.deleteTagById(tagId)
    }
    
    private fun TagEntity.toDomain() = Tag(
        id = id,
        userId = userId,
        name = name,
        color = color,
        icon = icon,
        parentId = parentId,
        isSystem = isSystem,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
    
    private fun Tag.toEntity() = TagEntity(
        id = id,
        userId = userId,
        name = name,
        color = color,
        icon = icon,
        parentId = parentId,
        isSystem = isSystem,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
