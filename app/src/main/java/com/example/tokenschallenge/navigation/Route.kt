package com.example.tokenschallenge.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    object ProfileScreen

    @Serializable
    object LogInScreen

    @Serializable
    object SplashScreen
}