package com.example.qrgenerator.data.mapper

import com.example.qrgenerator.data.model.QrDto
import com.example.qrgenerator.data.model.QrScanDto
import com.example.qrgenerator.data.model.UserDto
import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.model.QrScanDomain
import com.example.qrgenerator.domain.model.UserDomain


fun QrDto.toDomain() = QrDomain(
    id = id,
    redirectUrl = redirectUrl,
    title = title,
    fgColor = fgColor,
    bgColor = bgColor,
    ownerUid = ownerUid,
    expiresAt = expiresAt,
    createdAt = createdAt
)

fun QrDomain.toDto() = QrDto(
    id = id,
    redirectUrl = redirectUrl,
    title = title,
    fgColor = fgColor,
    bgColor = bgColor,
    ownerUid = ownerUid,
    expiresAt = expiresAt,
    createdAt = createdAt
)

fun UserDto.toDomain(): UserDomain =
    UserDomain(
        id = uid,
        email = email
    )

fun UserDomain.toDto(): UserDto =
    UserDto(
        uid = id,
        email = email
    )

fun QrScanDto.toDomain() = QrScanDomain(
    qrId = qrId,
    timestamp = timestamp?.toDate()?.time ?: 0L,
    country = country,
    state = state,
    city = city
)

fun QrScanDomain.toDto() = QrScanDto(
    qrId = qrId,
    timestamp = com.google.firebase.Timestamp(timestamp / 1000, 0),
    country = country,
    state = state,
    city = city
)