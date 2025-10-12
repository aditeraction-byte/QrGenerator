package com.example.qrgenerator.data.model
/**
 * Data Transfer Object representing a User in Firestore.
 */
data class UserDto(
    val uid: String = "",
    val email: String = ""
)