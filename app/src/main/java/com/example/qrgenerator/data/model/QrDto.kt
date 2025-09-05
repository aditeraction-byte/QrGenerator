package com.example.qrgenerator.data.model

data class QrDto(
    val id: String = "",
    val redirectUrl: String = "",
    val title: String = "",
    val fgColor: String = "#000000",
    val bgColor: String = "#FFFFFF",
    val expiresAt: Long? = null,
    val createdAt: Long = 0L
)