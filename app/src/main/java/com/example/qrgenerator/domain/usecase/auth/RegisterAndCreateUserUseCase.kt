package com.example.qrgenerator.domain.usecase.auth

import com.example.qrgenerator.domain.model.UserDomain
import com.example.qrgenerator.domain.repository.AuthRepository
import com.example.qrgenerator.domain.repository.UserRepository
import javax.inject.Inject
/**
 * Use case for registering a user and creating its Firestore document.
 */
class RegisterAndCreateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<UserDomain> {
        return authRepository.register(email, password).fold(
            onSuccess = { user ->
                userRepository.createUser(user)
                Result.success(user)
            },
            onFailure = { e ->
                Result.failure(e)
            }
        )
    }
}