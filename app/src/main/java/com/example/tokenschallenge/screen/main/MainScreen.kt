package com.example.tokenschallenge.screen.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.tokenschallenge.mainActivity.MainActivity
import com.example.tokenschallenge.navigation.AllNavigation
import com.example.tokenschallenge.screen.login.LogInViewModel
import com.example.tokenschallenge.screen.profile.ProfileViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
class RefreshRequest(
    val refreshToken: String
)

@Composable
fun MainScreen(
    mainActivity: MainActivity,
    logInViewModel: LogInViewModel = koinViewModel(),
    profileViewModel: ProfileViewModel = koinViewModel(),
    appViewModel: AppViewModel = koinViewModel(),
    navController: NavHostController
) {
    AllNavigation(navController =navController,
        logInViewModel = logInViewModel,
        profileViewModel = profileViewModel,
        appViewModel=appViewModel

        )
}

