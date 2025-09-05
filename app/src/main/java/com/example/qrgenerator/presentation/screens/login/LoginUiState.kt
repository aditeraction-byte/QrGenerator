package com.example.qrgenerator.presentation.screens.login

import com.example.qrgenerator.domain.model.UserDomain

sealed class LoginUIState {
    object Idle : LoginUIState()
    object Loading : LoginUIState()
    object LoggedOut : LoginUIState()
    data class Success(val user: UserDomain) : LoginUIState()
    data class Error(val message: String) : LoginUIState()
}