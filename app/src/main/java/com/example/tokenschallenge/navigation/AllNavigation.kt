package com.example.tokenschallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tokenschallenge.screen.splash.SplashScreen
import com.example.tokenschallenge.screen.profile.ProfileScreen
import com.example.tokenschallenge.screen.login.LoginScreen

@Composable
fun AllNavigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Route.SplashScreen
    ) {
        composable<Route.SplashScreen> {
            SplashScreen(
                navigateToLogin = {
                    navController.navigate(Route.LogInScreen) {
                        popUpTo(Route.SplashScreen) {
                            inclusive = true
                        }
                    }
                },
                navigateToProfile = {
                    navController.navigate(
                        Route.ProfileScreen
                    ){
                        popUpTo(Route.SplashScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Route.LogInScreen> {
            LoginScreen(
                navigateToProfile = {
                    navController.navigate(Route.ProfileScreen){
                        popUpTo(Route.LogInScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Route.ProfileScreen> {
            ProfileScreen(
                navigateToLogin = {
                    navController.navigate(Route.LogInScreen)
                }
            )
        }
    }


}