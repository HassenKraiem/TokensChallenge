package com.example.tokenschallenge.screen.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.tokenschallenge.navigation.AllNavigation
import com.example.tokenschallenge.screen.login.LogInViewModel
import com.example.tokenschallenge.screen.profile.ProfileViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
class RefreshRequest(
    val refreshToken: String
)

// Todo: Create view models with screens
@Composable
fun MainScreen(
    navController: NavHostController
) {
    AllNavigation(
        navController = navController,
    )
}

