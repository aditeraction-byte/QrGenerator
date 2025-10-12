package com.example.qrgenerator.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.qrgenerator.presentation.screens.generator.QrScreen
import com.example.qrgenerator.presentation.screens.home.HomeScreen
import com.example.qrgenerator.presentation.screens.login.LoginScreen
import com.example.qrgenerator.presentation.screens.qrDetail.QrDetailsScreen
import com.example.qrgenerator.presentation.screens.qrStats.QrStatsScreen

/**
 * Sets up the main navigation of the application using Jetpack Compose Navigation.
 *
 * Implemented navigation's:
 * - Login -> Home
 * - Home -> QrGenerator, QrDetails, QrStats
 * - QrGenerator -> Home (on back)
 * - QrDetails -> Home (on back)
 * - QrStats -> Home (on back)
 */
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    var reloadTrigger by remember { mutableStateOf(false) } // Forces recomposition in Home when returning

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        // Login Screen
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) { popUpTo(Screen.Login.route) { inclusive = true } }
                }
            )
        }
        // Home Screen
        composable(Screen.Home.route) {
            HomeScreen(
                onCreateQr = { navController.navigate(Screen.QrGenerator.route) },
                onQrDetail = { qrId -> navController.navigate(Screen.QrDetails.createRoute(qrId)) },
                onQrStats = { qrId -> navController.navigate(Screen.QrStats.createRoute(qrId)) },
                reloadTrigger = reloadTrigger,
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
        // QR Generator Screen
        composable(Screen.QrGenerator.route) {
            QrScreen(
                onBack = {
                    navController.popBackStack()
                    reloadTrigger = !reloadTrigger
                }
            )
        }
        // QR Details Screen
        composable(
            route = "qr_details/{qrId}",
            arguments = listOf(navArgument("qrId") { type = NavType.StringType })
        ) { backStackEntry ->
            val qrId = backStackEntry.arguments?.getString("qrId") ?: return@composable
            QrDetailsScreen(qrId = qrId, onBack = { navController.popBackStack() })
        }
        // QR Stats Screen
        composable(Screen.QrStats.route) { backStackEntry ->
            val qrId = backStackEntry.arguments?.getString("qrId") ?: ""
            QrStatsScreen(qrId = qrId, onBack = { navController.popBackStack() })
        }
    }
}