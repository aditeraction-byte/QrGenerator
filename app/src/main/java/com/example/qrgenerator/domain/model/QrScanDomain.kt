package com.example.qrgenerator.domain.model

data class QrScanDomain(
    val qrId: String,
    val timestamp: Long,
    val country: String? = null,
    val state: String? = null,
    val city: String? = null
)