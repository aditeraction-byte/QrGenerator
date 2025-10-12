package com.example.qrgenerator.data.repository

import com.example.qrgenerator.data.mapper.toDomain
import com.example.qrgenerator.data.model.UserDto
import com.example.qrgenerator.domain.model.UserDomain
import com.example.qrgenerator.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
/**
 * Implementation of AuthRepository using FirebaseAuth.
 * Handles login, registration, logout, and retrieving current user.
 */
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {
    /**
     * Logs in a user with email and password.
     * Returns UserDomain if successful, or a failure result.
     */
    override suspend fun login(email: String, password: String): Result<UserDomain> =
        suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener {
                    val firebaseUser = auth.currentUser
                    if (firebaseUser != null) {
                        val dto = UserDto(uid = firebaseUser.uid, email = firebaseUser.email ?: "")
                        continuation.resume(Result.success(dto.toDomain()))
                    }else {
                        continuation.resume(Result.failure(Exception("User not found")))
                    }
                }
                .addOnFailureListener { continuation.resume(Result.failure(it)) }
        }
    /**
     * Registers a new user with email and password.
     */
    override suspend fun register(email: String, password: String): Result<UserDomain> =
        suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val firebaseUser = auth.currentUser
                    if (firebaseUser != null) {
                        val dto = UserDto(uid = firebaseUser.uid, email = firebaseUser.email ?: "")
                        continuation.resume(Result.success(dto.toDomain()))
                    } else {
                        continuation.resume(Result.failure(Exception("Registration failed")))
                    }
                }
                .addOnFailureListener { continuation.resume(Result.failure(it)) }
        }
    /**
     * Logs out the currently authenticated user.
     */
    override suspend fun logout(): Result<Unit> = try {
        auth.signOut()
        Result.success(Unit)
    }catch (e: Exception){
        Result.failure(e)
    }
    /**
     * Returns the currently logged-in user, or null if no user is authenticated.
     */
    override suspend fun getCurrentUser(): UserDomain? {
        val firebaseUser = auth.currentUser
        return firebaseUser?.let { UserDto(it.uid, it.email ?: "").toDomain() }
    }
}