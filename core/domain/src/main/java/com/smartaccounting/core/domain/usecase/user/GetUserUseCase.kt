package com.smartaccounting.core.domain.usecase.user

import com.smartaccounting.core.domain.model.User
import com.smartaccounting.core.domain.repository.UserRepository

class GetUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): Result<User> {
        return try {
            val user = userRepository.getUserById(userId)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("用户不存在"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
