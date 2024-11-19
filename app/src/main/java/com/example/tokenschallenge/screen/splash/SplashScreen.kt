package com.example.tokenschallenge.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel


@Composable
fun SplashScreen(
    navigateToLogin:()->Unit,
    navigateToProfile:()->Unit,
    splashViewModel: SplashViewModel= koinViewModel()
){
    val splashUiState by splashViewModel.splashState.collectAsState()
    LaunchedEffect(Unit) {
        delay(2000L)
        if (splashUiState.isLoggedIn) {
            navigateToProfile()
        }
        else{
            navigateToLogin()

        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Text(
            text = "Hassen Lite",
            fontSize = 100.sp,
            lineHeight = 200.sp,
            fontWeight = FontWeight.Bold
        )


    }
}