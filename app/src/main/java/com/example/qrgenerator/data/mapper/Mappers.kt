package com.example.qrgenerator.data.mapper

import com.example.qrgenerator.data.model.QrDto
import com.example.qrgenerator.data.model.UserDto
import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.model.UserDomain

fun QrDto.toDomain(): QrDomain {
    return QrDomain(id = this.id, redirectUrl = this.redirectUrl)
}

fun UserDto.toDomain(): UserDomain = UserDomain(
    id = uid,
    email = email
)

fun UserDomain.toDto(): UserDto = UserDto(
    uid = id,
    email = email
)