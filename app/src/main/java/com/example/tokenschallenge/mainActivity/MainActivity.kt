package com.example.tokenschallenge.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.tokenschallenge.di.ApiModule
import com.example.tokenschallenge.di.DataStoreModule
import com.example.tokenschallenge.di.ViewModelModelModule
import com.example.tokenschallenge.screen.main.MainScreen
import com.example.tokenschallenge.ui.theme.TokensChallengeTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(
                DataStoreModule().module,
                ApiModule().module,
                ViewModelModelModule().module,

                )
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            TokensChallengeTheme {
                val navController = rememberNavController()
                MainScreen(
                    navController = navController
                )
            }
        }
    }
}

