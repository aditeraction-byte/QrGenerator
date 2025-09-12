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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.qrgenerator.presentation.screens.generator.QrScreen
import com.example.qrgenerator.presentation.screens.home.HomeScreen
import com.example.qrgenerator.presentation.screens.login.LoginScreen
import com.example.qrgenerator.presentation.screens.qrStats.QrStatsScreen
import com.google.common.base.Defaults.defaultValue

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    var reloadTrigger by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // --- Login ---
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // --- Home ---
        composable(Screen.Home.route) {
            HomeScreen(
                onCreateQr = { navController.navigate(Screen.QrGenerator.createRoute()) },
                onQrDetail = { qrId -> navController.navigate(Screen.QrGenerator.createRoute(qrId)) },
                onQrStats = { qrId -> navController.navigate(Screen.QrStats.createRoute(qrId)) },
                reloadTrigger = reloadTrigger
            )
        }

        // --- Generator (crear o editar) ---
        composable(
            route = Screen.QrGenerator.route,
            arguments = listOf(
                navArgument("qrId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val qrId = backStackEntry.arguments?.getString("qrId")
            QrScreen(
                qrId = qrId,
                onBack = {
                    navController.popBackStack()
                    reloadTrigger = !reloadTrigger
                }
            )
        }

        // --- Stats ---
        composable(Screen.QrStats.route) { backStackEntry ->
            val qrId = backStackEntry.arguments?.getString("qrId") ?: ""
            QrStatsScreen(
                qrId = qrId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}