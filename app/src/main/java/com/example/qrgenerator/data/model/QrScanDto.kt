package com.example.qrgenerator.data.model

data class QrScanDto(
    val qrId: String = "",
    val timestamp: com.google.firebase.Timestamp? = null,
    val country: String? = null,
    val state: String? = null,
    val city: String? = null
)


