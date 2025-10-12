package com.example.qrgenerator.domain.repository

import com.example.qrgenerator.domain.model.UserDomain

/**
 * Repository for authentication operations.
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): Result<UserDomain>
    suspend fun register(email: String, password: String): Result<UserDomain>
    suspend fun logout(): Result<Unit>
    suspend fun getCurrentUser(): UserDomain?
}