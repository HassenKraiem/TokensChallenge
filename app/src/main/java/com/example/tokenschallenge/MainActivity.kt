package com.example.tokenschallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.tokenschallenge.dataStore.DataStoreManager
import com.example.tokenschallenge.dataStore.preferenceData
import com.example.tokenschallenge.screen.LogScreen
import com.example.tokenschallenge.screen.MainScreen
import com.example.tokenschallenge.ui.theme.TokensChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TokensChallengeTheme {
                val dataStoreContext = LocalContext.current
                val dataStoreManager = DataStoreManager(dataStoreContext)
               MainScreen(
                   mainActivity = this@MainActivity,
                   preferenceDataStore =preferenceData,
                   dataStoreManager=dataStoreManager
               )
            }
        }
    }
}

