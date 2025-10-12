package com.example.qrgenerator.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Top app bar with optional logout button.
 *
 * @param title Title to display.
 * @param showLogout Whether to show logout action.
 * @param onLogout Lambda executed on logout click.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    showLogout: Boolean = false,
    onLogout: () -> Unit = {}
) {
    TopAppBar(
        title = { AppText(title, color = Color.White) },
        actions = {
            if (showLogout) {
                IconButton(onClick = onLogout) {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout", tint = Color.White)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF7A4DCC))
    )
}