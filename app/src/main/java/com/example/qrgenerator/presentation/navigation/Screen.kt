package com.example.qrgenerator.presentation.navigation

/**
 * Represents the different screens of the application and their routes.
 * For screens that require parameters, the `createRoute` function is provided.
 */
sealed class Screen(val route: String) {
    /** Login screen */
    object Login : Screen("login")
    /** Main / Home screen */
    object Home : Screen("home")
    /** QR statistics screen. Requires a qrId parameter */
    object QrStats : Screen("qr_stats/{qrId}") {
        fun createRoute(qrId: String) = "qr_stats/$qrId"
    }
    /** Screen to generate a new QR code */
    object QrGenerator : Screen("qr_generator")
    /** QR details screen. Requires a qrId parameter */
    object QrDetails : Screen("qr_details/{qrId}") {
        fun createRoute(qrId: String) = "qr_details/$qrId"
    }
}