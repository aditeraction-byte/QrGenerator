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

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    var reloadTrigger by remember { mutableStateOf(false) }

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) { popUpTo(Screen.Login.route) { inclusive = true } }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onCreateQr = { navController.navigate(Screen.QrGenerator.route) },
                onQrDetail = { qrId -> navController.navigate(Screen.QrDetails.createRoute(qrId)) },
                onQrStats = { qrId -> navController.navigate(Screen.QrStats.createRoute(qrId)) },
                reloadTrigger = reloadTrigger
            )
        }

        composable(Screen.QrGenerator.route) {
            QrScreen(
                onBack = {
                    navController.popBackStack()
                    reloadTrigger = !reloadTrigger
                }
            )
        }

        composable(
            route = "qr_details/{qrId}",
            arguments = listOf(navArgument("qrId") { type = NavType.StringType })
        ) { backStackEntry ->
            val qrId = backStackEntry.arguments?.getString("qrId") ?: return@composable
            QrDetailsScreen(qrId = qrId, onBack = { navController.popBackStack() })
        }

        composable(Screen.QrStats.route) { backStackEntry ->
            val qrId = backStackEntry.arguments?.getString("qrId") ?: ""
            QrStatsScreen(qrId = qrId, onBack = { navController.popBackStack() })
        }
    }
}