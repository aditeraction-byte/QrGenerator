package com.example.qrgenerator.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.qrgenerator.presentation.screens.generator.QrScreen
import com.example.qrgenerator.presentation.screens.home.HomeScreen
import com.example.qrgenerator.presentation.screens.login.LoginScreen
import com.example.qrgenerator.presentation.screens.qrDetail.QrDetailScreen
import com.example.qrgenerator.presentation.screens.qrStats.QrStatsScreen

@Composable
fun AppNavHost(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Home.route){
            HomeScreen(navController)
        }
        composable(Screen.CreateQr.route) {
            QrScreen()
        }
        composable(Screen.QrDetail.route) { backStackEntry ->
            val qrId = backStackEntry.arguments?.getString("qrId") ?: ""
            QrDetailScreen(navController, qrId)
        }
        composable(Screen.QrStats.route) { backStackEntry ->
            val qrId = backStackEntry.arguments?.getString("qrId") ?: ""
            QrStatsScreen(navController, qrId)

        }
    }
}