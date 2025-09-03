package com.example.qrgenerator.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object CreateQr : Screen("create_qr")
    object QrDetail : Screen("qr_detail/{qrId}") {
        fun createRoute(qrId: String) = "qr_detail/$qrId"
    }
    object QrStats : Screen("qr_stats/{qrId}") {
        fun createRoute(qrId: String) = "qr_stats/$qrId"
    }
}