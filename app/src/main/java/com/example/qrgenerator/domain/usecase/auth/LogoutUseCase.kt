package com.example.qrgenerator.domain.usecase.auth

import com.example.qrgenerator.domain.repository.AuthRepository
/**
 * Use case for logging out the current user.
 */
class LogoutUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): Unit =
        repository.logout().getOrThrow()
}