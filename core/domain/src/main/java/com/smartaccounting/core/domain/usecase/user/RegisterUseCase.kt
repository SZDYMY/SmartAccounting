package com.smartaccounting.core.domain.usecase.user

import com.smartaccounting.core.domain.model.User
import com.smartaccounting.core.domain.repository.UserRepository

class RegisterUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(phone: String, password: String, nickname: String): Result<User> {
        return try {
            val existingUser = userRepository.getUserByPhone(phone)
            if (existingUser != null) {
                Result.failure(Exception("该手机号已注册"))
            } else {
                val passwordHash = hashPassword(password)
                val user = userRepository.register(phone, passwordHash, nickname)
                Result.success(user)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun hashPassword(password: String): String {
        return password.hashCode().toString()
    }
}
