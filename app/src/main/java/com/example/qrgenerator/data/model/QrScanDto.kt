package com.example.qrgenerator.data.model
/**
 * Data Transfer Object representing a scan of a QR code.
 * Includes location info and timestamp.
 */
data class QrScanDto(
    val qrId: String = "",
    val timestamp: com.google.firebase.Timestamp? = null,
    val country: String? = null,
    val state: String? = null,
    val city: String? = null
)


