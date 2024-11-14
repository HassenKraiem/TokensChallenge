package com.example.tokenschallenge.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tokenschallenge.MainActivity
import com.example.tokenschallenge.navigation.AllNavigation
import com.example.tokenschallenge.screen.login.LoginScreen
import com.example.tokenschallenge.ui.AppViewModel
import com.example.tokenschallenge.ui.LogInViewModel
import com.example.tokenschallenge.ui.ProfileViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
class RefreshRequest(
    val refreshToken: String
)

@Composable
fun MainScreen(
    mainActivity: MainActivity,
    preferenceDataStore: DataStore<Preferences>,
    logInViewModel: LogInViewModel = koinViewModel(),
    profileViewModel: ProfileViewModel= koinViewModel(),
    appViewModel: AppViewModel= koinViewModel(),
    navController: NavHostController
) {
    val uiState by logInViewModel.logInUiState.collectAsState()
   /* LaunchedEffect(Unit) {
        logInViewModel.checkRegisterState(preferenceDataStore = preferenceDataStore)
    }*/
    /*if (uiState.isLoggedIn) {
        ProfileScreen(profileViewModel,)
    } else {
        LoginScreen(
            logInViewModel = logInViewModel,

            )
    }*/
    AllNavigation(navController =navController,
        logInViewModel = logInViewModel,
        profileViewModel = profileViewModel,
        appViewModel=appViewModel

        )


}

