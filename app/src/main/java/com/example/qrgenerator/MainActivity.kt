package com.example.qrgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.qrgenerator.presentation.navigation.AppNavHost
import com.example.qrgenerator.ui.theme.QRGeneratorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRGeneratorTheme {
                AppNavHost()
            }
        }
    }
}
