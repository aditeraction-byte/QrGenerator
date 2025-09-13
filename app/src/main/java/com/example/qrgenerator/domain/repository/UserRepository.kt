package com.example.qrgenerator.domain.repository

import com.example.qrgenerator.domain.model.UserDomain

interface UserRepository {
    suspend fun createUser(user: UserDomain): Result<Unit>
    suspend fun getUser(userId: String): UserDomain?
    suspend fun updateUser(user: UserDomain): Result<Unit>
}