package com.example.qrgenerator.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.qrgenerator.presentation.screens.generator.QrScreen
import com.example.qrgenerator.presentation.screens.home.HomeScreen
import com.example.qrgenerator.presentation.screens.login.LoginScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    var reloadTrigger by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onCreateQr = { navController.navigate(Screen.CreateQr.route) },
                onQrDetail = { qrId -> navController.navigate(Screen.QrDetail.createRoute(qrId)) },
                onQrStats = { qrId -> navController.navigate(Screen.QrStats.createRoute(qrId)) },
                reloadTrigger = reloadTrigger
            )
        }

        composable(Screen.CreateQr.route) {
            QrScreen(
                onBack = {
                    navController.popBackStack()
                    reloadTrigger = !reloadTrigger
                }
            )
        }

        composable(Screen.QrDetail.route) { backStackEntry ->
            val qrId = backStackEntry.arguments?.getString("qrId") ?: ""
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("QR Detail Screen for ID: $qrId")
            }
        }

        composable(Screen.QrStats.route) { backStackEntry ->
            val qrId = backStackEntry.arguments?.getString("qrId") ?: ""
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("QR Stats Screen for ID: $qrId")
            }
        }
    }
}