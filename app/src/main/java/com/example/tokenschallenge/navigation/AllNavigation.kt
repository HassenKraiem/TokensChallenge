package com.example.tokenschallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tokenschallenge.screen.EnterScreen
import com.example.tokenschallenge.screen.profile.ProfileScreen
import com.example.tokenschallenge.screen.login.LoginScreen
import com.example.tokenschallenge.screen.main.AppViewModel
import com.example.tokenschallenge.screen.login.LogInViewModel
import com.example.tokenschallenge.screen.profile.ProfileViewModel

@Composable
fun AllNavigation(
    navController: NavHostController,
    logInViewModel: LogInViewModel,
    profileViewModel: ProfileViewModel,
    appViewModel: AppViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Route.EnterScreen
    ) {
        composable<Route.EnterScreen> {
            EnterScreen {
                appViewModel.onEnter(
                    navController = navController
                )
            }
        }
        composable<Route.LogInScreen> {
            LoginScreen(
                logInViewModel
            ) { appViewModel.onLoggedIn(navController) }
        }

        composable<Route.ProfileScreen> {
            ProfileScreen(
                profileViewModel
            ) {
                appViewModel.onLogout(
                    navController = navController
                )
            }
        }
    }


}