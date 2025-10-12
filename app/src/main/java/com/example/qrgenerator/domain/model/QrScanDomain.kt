package com.example.qrgenerator.domain.model

/**
 * Domain model representing a scan of a QR code.
 *
 * @property qrId The ID of the scanned QR code.
 * @property timestamp The time when the scan occurred.
 * @property country Optional country of the scanner.
 * @property state Optional state/region of the scanner.
 * @property city Optional city of the scanner.
 */
data class QrScanDomain(
    val qrId: String,
    val timestamp: Long,
    val country: String? = null,
    val state: String? = null,
    val city: String? = null
)