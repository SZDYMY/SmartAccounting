package com.smartaccounting.core.data.repository

import com.smartaccounting.core.data.local.dao.UserDao
import com.smartaccounting.core.data.local.entity.UserEntity
import com.smartaccounting.core.domain.model.User
import com.smartaccounting.core.domain.repository.UserRepository
import java.util.UUID

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    
    override suspend fun getUserById(userId: String): User? {
        return userDao.getUserById(userId)?.toDomain()
    }
    
    override suspend fun getUserByPhone(phone: String): User? {
        return userDao.getUserByPhone(phone)?.toDomain()
    }
    
    override suspend fun login(phone: String, passwordHash: String): User? {
        return userDao.login(phone, passwordHash)?.toDomain()
    }
    
    override suspend fun register(phone: String, passwordHash: String, nickname: String): User {
        val userEntity = UserEntity(
            id = UUID.randomUUID().toString(),
            phone = phone,
            nickname = nickname,
            avatar = null,
            email = null,
            passwordHash = passwordHash,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        userDao.insertUser(userEntity)
        return userEntity.toDomain()
    }
    
    override suspend fun updateUser(user: User) {
        userDao.updateUser(user.toEntity())
    }
    
    override suspend fun deleteUser(userId: String) {
        userDao.deleteUserById(userId)
    }
    
    override suspend fun verifyCode(phone: String, code: String): Boolean {
        return true
    }
    
    override suspend fun resetPassword(phone: String, newPasswordHash: String): Boolean {
        val user = userDao.getUserByPhone(phone)
        return if (user != null) {
            userDao.updateUser(user.copy(passwordHash = newPasswordHash, updatedAt = System.currentTimeMillis()))
            true
        } else {
            false
        }
    }
    
    private fun UserEntity.toDomain() = User(
        id = id,
        phone = phone,
        nickname = nickname,
        avatar = avatar,
        email = email,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
    
    private fun User.toEntity() = UserEntity(
        id = id,
        phone = phone,
        nickname = nickname,
        avatar = avatar,
        email = email,
        passwordHash = "",
        createdAt = createdAt,
        updatedAt = System.currentTimeMillis()
    )
}
