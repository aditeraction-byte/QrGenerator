package com.example.qrgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.qrgenerator.presentation.navigation.AppNavHost
import com.example.qrgenerator.ui.theme.QRGeneratorTheme
import dagger.hilt.android.AndroidEntryPoint

// Main entry point of the application.
// Annotated with @AndroidEntryPoint to enable Hilt dependency injection.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Applies the global app theme and sets the navigation host as root content.
            QRGeneratorTheme {
                AppNavHost()
            }
        }
    }
}
