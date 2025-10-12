package com.example.qrgenerator.domain.usecase.auth

import com.example.qrgenerator.domain.model.UserDomain
import com.example.qrgenerator.domain.repository.AuthRepository
/**
 * Use case for logging in a user.
 */
class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): UserDomain =
        repository.login(email, password).getOrThrow()
}