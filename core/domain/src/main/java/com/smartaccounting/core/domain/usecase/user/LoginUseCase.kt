package com.smartaccounting.core.domain.usecase.user

import com.smartaccounting.core.domain.model.User
import com.smartaccounting.core.domain.repository.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(phone: String, password: String): Result<User> {
        return try {
            val passwordHash = hashPassword(password)
            val user = userRepository.login(phone, passwordHash)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("手机号或密码错误"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun hashPassword(password: String): String {
        return password.hashCode().toString()
    }
}
