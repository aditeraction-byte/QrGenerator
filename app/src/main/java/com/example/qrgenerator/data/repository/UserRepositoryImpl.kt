package com.example.qrgenerator.data.repository

import com.example.qrgenerator.data.mapper.toDomain
import com.example.qrgenerator.data.model.UserDto
import com.example.qrgenerator.domain.model.UserDomain
import com.example.qrgenerator.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRepository {

    override suspend fun createUser(user: UserDomain): Result<Unit> = try {
        firestore.collection("users")
            .document(user.id)
            .set(user)
            .await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getUser(userId: String): UserDomain? {
        val doc = firestore.collection("users").document(userId).get().await()
        return doc.toObject(UserDto::class.java)?.toDomain()
    }

    override suspend fun updateUser(user: UserDomain): Result<Unit> = try {
        firestore.collection("users")
            .document(user.id)
            .set(user)
            .await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}