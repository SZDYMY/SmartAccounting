package com.smartaccounting.core.data.local.dao

import androidx.room.*
import com.smartaccounting.core.data.local.entity.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {
    @Query("SELECT * FROM tags WHERE userId IS NULL OR userId = :userId ORDER BY isSystem DESC, name ASC")
    fun getAllTags(userId: String): Flow<List<TagEntity>>
    
    @Query("SELECT * FROM tags WHERE parentId IS NULL AND (userId IS NULL OR userId = :userId)")
    fun getParentTags(userId: String): Flow<List<TagEntity>>
    
    @Query("SELECT * FROM tags WHERE parentId = :parentId")
    fun getChildTags(parentId: String): Flow<List<TagEntity>>
    
    @Query("SELECT * FROM tags WHERE id = :tagId")
    suspend fun getTagById(tagId: String): TagEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: TagEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTags(tags: List<TagEntity>)
    
    @Update
    suspend fun updateTag(tag: TagEntity)
    
    @Delete
    suspend fun deleteTag(tag: TagEntity)
    
    @Query("DELETE FROM tags WHERE id = :tagId")
    suspend fun deleteTagById(tagId: String)
}
