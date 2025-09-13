package com.example.qrgenerator.domain.model

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
    val shortLink: String
        get() = "https://adit-qr.web.app/qr.html?id=$id"
}