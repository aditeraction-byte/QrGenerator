package com.example.qrgenerator.domain.model

/**
 * Domain model representing a user.
 *
 * @property id Unique user identifier.
 * @property email User email address.
 */
data class UserDomain(
    val id: String,
    val email: String
)