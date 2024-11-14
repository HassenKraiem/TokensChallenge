package com.example.tokenschallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tokenschallenge.screen.ProfileScreen
import com.example.tokenschallenge.screen.login.LoginScreen
import com.example.tokenschallenge.ui.AppViewModel
import com.example.tokenschallenge.ui.LogInViewModel
import com.example.tokenschallenge.ui.ProfileViewModel

@Composable
fun AllNavigation(
    navController: NavHostController,
    logInViewModel: LogInViewModel,
    profileViewModel: ProfileViewModel,
    appViewModel: AppViewModel
) {
    val uiState by appViewModel.logState.collectAsState()
    NavHost(
        navController = navController,
        startDestination = if (uiState.isLoggedIn) Route.ProfileScreen
        else Route.LogInScreen
    ) {
        composable<Route.LogInScreen> {
            LoginScreen(
                logInViewModel
            ) { appViewModel.onLoggedIn() }
        }

        composable<Route.ProfileScreen> {
            ProfileScreen(
                profileViewModel
            ) { appViewModel.onLogout() }
        }
    }


}