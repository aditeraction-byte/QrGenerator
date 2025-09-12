package com.example.qrgenerator.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object QrStats : Screen("qr_stats/{qrId}") {
        fun createRoute(qrId: String) = "qr_stats/$qrId"
    }

    // QrGenerator (creación/edición en la misma pantalla)
    object QrGenerator : Screen("qr_generator?qrId={qrId}") {
        fun createRoute(qrId: String? = null): String {
            return if (qrId != null) {
                "qr_generator?qrId=$qrId"
            } else {
                "qr_generator"
            }
        }
    }
}