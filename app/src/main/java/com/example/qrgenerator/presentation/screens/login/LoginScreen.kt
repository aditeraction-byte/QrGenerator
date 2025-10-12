package com.example.qrgenerator.presentation.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.qrgenerator.presentation.components.AppButton
import com.example.qrgenerator.presentation.components.AppCard
import com.example.qrgenerator.presentation.components.AppText
import com.example.qrgenerator.presentation.components.AppTextField

/**
 * Composable for the Login screen.
 * Displays email/password input, login/register buttons, and handles loading/error states.
 *
 * @param viewModel ViewModel handling login/registration logic.
 * @param onLoginSuccess Callback triggered when login or registration succeeds.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
) {
    // Local state for text fields
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Collect current UI state from ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Background gradient and main container
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF7A4DCC), Color(0xFF5AA0FF))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Card container for input fields and buttons
            AppCard(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Welcome title
                    AppText(
                        text = "Welcome",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF5AA0FF)
                    )

                    Spacer(Modifier.height(16.dp))

                    // Email input
                    AppTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    // Password input
                    AppTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    // Login button
                    AppButton(
                        onClick = { viewModel.login(email, password) },
                        text = "Login",
                        enabled = uiState !is LoginUIState.Loading,
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color(0xFF5AA0FF)
                    )

                    Spacer(Modifier.height(8.dp))

                    // Register button
                    AppButton(
                        onClick = { viewModel.register(email, password) },
                        text = "Register",
                        enabled = uiState !is LoginUIState.Loading,
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color(0xFF7A4DCC)
                    )

                    // Error message display
                    if (uiState is LoginUIState.Error) {
                        Spacer(Modifier.height(12.dp))
                        AppText(
                            text = (uiState as LoginUIState.Error).message,
                            color = Color.Red
                        )
                    }
                }
            }
        }

        // Overlay for loading state
        if (uiState is LoginUIState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        // Trigger callback when login/register succeeds
        if (uiState is LoginUIState.Success) {
            LaunchedEffect(Unit) { onLoginSuccess() }
        }
    }
}
