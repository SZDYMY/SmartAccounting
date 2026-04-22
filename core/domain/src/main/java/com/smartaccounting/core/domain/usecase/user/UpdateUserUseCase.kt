package com.smartaccounting.core.domain.usecase.user

import com.smartaccounting.core.domain.model.User
import com.smartaccounting.core.domain.repository.UserRepository

class UpdateUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): Result<Unit> {
        return try {
            userRepository.updateUser(user)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
