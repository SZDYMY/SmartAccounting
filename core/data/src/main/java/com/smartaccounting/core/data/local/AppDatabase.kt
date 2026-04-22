package com.smartaccounting.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.smartaccounting.core.data.local.dao.BillDao
import com.smartaccounting.core.data.local.dao.TagDao
import com.smartaccounting.core.data.local.dao.UserDao
import com.smartaccounting.core.data.local.entity.BillEntity
import com.smartaccounting.core.data.local.entity.TagEntity
import com.smartaccounting.core.data.local.entity.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@Database(
    entities = [UserEntity::class, BillEntity::class, TagEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun billDao(): BillDao
    abstract fun tagDao(): TagDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smart_accounting_database"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
        
        private class DatabaseCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDefaultTags(database.tagDao())
                    }
                }
            }
            
            private suspend fun populateDefaultTags(tagDao: TagDao) {
                val defaultTags = listOf(
                    TagEntity(UUID.randomUUID().toString(), null, "餐饮", "#FF6B6B", "restaurant", null, true, System.currentTimeMillis(), System.currentTimeMillis()),
                    TagEntity(UUID.randomUUID().toString(), null, "交通", "#4ECDC4", "car", null, true, System.currentTimeMillis(), System.currentTimeMillis()),
                    TagEntity(UUID.randomUUID().toString(), null, "购物", "#45B7D1", "shopping", null, true, System.currentTimeMillis(), System.currentTimeMillis()),
                    TagEntity(UUID.randomUUID().toString(), null, "娱乐", "#96CEB4", "game", null, true, System.currentTimeMillis(), System.currentTimeMillis()),
                    TagEntity(UUID.randomUUID().toString(), null, "其他", "#DDA0DD", "ellipsis", null, true, System.currentTimeMillis(), System.currentTimeMillis())
                )
                tagDao.insertTags(defaultTags)
            }
        }
    }
}
