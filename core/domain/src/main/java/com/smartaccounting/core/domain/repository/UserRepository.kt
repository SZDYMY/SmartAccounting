package com.smartaccounting.core.domain.repository

import com.smartaccounting.core.domain.model.User

interface UserRepository {
    suspend fun getUserById(userId: String): User?
    suspend fun getUserByPhone(phone: String): User?
    suspend fun login(phone: String, passwordHash: String): User?
    suspend fun register(phone: String, passwordHash: String, nickname: String): User
    suspend fun updateUser(user: User)
    suspend fun deleteUser(userId: String)
    suspend fun verifyCode(phone: String, code: String): Boolean
    suspend fun resetPassword(phone: String, newPasswordHash: String): Boolean
}
