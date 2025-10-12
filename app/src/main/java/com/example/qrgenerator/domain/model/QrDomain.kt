package com.example.qrgenerator.domain.model

/**
 * Domain model representing a QR code.
 *
 * @property id Unique identifier of the QR code.
 * @property redirectUrl URL where the QR redirects.
 * @property title Optional title for the QR.
 * @property fgColor Foreground color in hex format. Default is black.
 * @property bgColor Background color in hex format. Default is white.
 * @property ownerUid UID of the user who owns this QR.
 * @property expiresAt Optional timestamp for QR expiration.
 * @property createdAt Timestamp when the QR was created.
 */

data class QrDomain(
    val id: String,
    val redirectUrl: String,
    val title: String = "",
    val fgColor: String = "#000000",
    val bgColor: String = "#FFFFFF",
    val ownerUid: String,
    val expiresAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
) {
    /**
     * Returns a short link to access this QR.
     */
    val shortLink: String
        get() = "https://adit-qr.web.app/qr.html?id=$id"
}