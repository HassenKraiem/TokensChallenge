package com.example.tokenschallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.tokenschallenge.dataStore.preferenceData
import com.example.tokenschallenge.di.Modules
import com.example.tokenschallenge.screen.MainScreen
import com.example.tokenschallenge.ui.theme.TokensChallengeTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(
                Modules
            )
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            TokensChallengeTheme {
                val navController= rememberNavController()
               // val dataStoreContext = LocalContext.current
               // val dataStoreManager = DataStoreManager(dataStoreContext)
               MainScreen(
                   mainActivity = this@MainActivity,
                   navController = navController
                   //dataStoreManager=dataStoreManager
               )
            }
        }
    }
}

