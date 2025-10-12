package com.example.qrgenerator.presentation.screens.login

import com.example.qrgenerator.domain.model.UserDomain

/**
 * Represents the different UI states of the Login screen.
 */
sealed class LoginUIState {
    object Idle : LoginUIState()                  // Initial state
    object Loading : LoginUIState()               // Operation in progress
    object LoggedOut : LoginUIState()            // User is logged out
    data class Success(val user: UserDomain) : LoginUIState() // Login/registration successful
    data class Error(val message: String) : LoginUIState()    // Operation failed
}