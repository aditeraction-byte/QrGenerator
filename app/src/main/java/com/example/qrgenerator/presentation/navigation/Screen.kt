package com.example.qrgenerator.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")

    object QrStats : Screen("qr_stats/{qrId}") {
        fun createRoute(qrId: String) = "qr_stats/$qrId"
    }

    object QrGenerator : Screen("qr_generator")

    object QrDetails : Screen("qr_details/{qrId}") {
        fun createRoute(qrId: String) = "qr_details/$qrId"
    }
}