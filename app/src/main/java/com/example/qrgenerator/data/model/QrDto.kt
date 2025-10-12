package com.example.qrgenerator.data.model
/**
 * Data Transfer Object representing a QR code in Firestore.
 * Contains metadata like colors, owner, expiration, and creation timestamp.
 */
data class QrDto(
    val id: String = "",
    val redirectUrl: String = "",
    val title: String = "",
    val fgColor: String = "#000000",
    val bgColor: String = "#FFFFFF",
    val ownerUid: String = "",
    val expiresAt: Long? = null,
    val createdAt: Long = 0L
)