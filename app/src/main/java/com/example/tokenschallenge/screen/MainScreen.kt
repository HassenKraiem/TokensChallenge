package com.example.tokenschallenge.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tokenschallenge.MainActivity
import com.example.tokenschallenge.dataStore.DataStoreManager
import com.example.tokenschallenge.dataStore.DataStoreManager.Companion.ACCESS_TOKEN
import com.example.tokenschallenge.dataStore.DataStoreManager.Companion.REFRESH_TOKEN
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@Composable
fun MainScreen(
    mainActivity: MainActivity,
    preferenceDataStore: DataStore<Preferences>,
    dataStoreManager: DataStoreManager
) {

    var isRegistered by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val onLogout = {
        isRegistered = false
        scope.launch {
            dataStoreManager.clearDataStore()
        }
    }
        LaunchedEffect(key1 = Unit) {
            checkRegisterState(preferenceDataStore) { it ->
                isRegistered = it
            }
        }
    if (isRegistered) {
        ProfileScreen(dataStoreManager,onLogout)
    } else {
        LogScreen(onRegisterSuccess={ isRegistered = true },
            dataStoreManager = dataStoreManager,
            appViewModel = viewModel()
        )
    }


}

suspend fun checkRegisterState(
    preferenceDataStore: DataStore<Preferences>,
    onResult: (Boolean) -> Unit
) {
    val preferences = preferenceDataStore.data.first()
    val accessToken = preferences[ACCESS_TOKEN]
    val isRegistered = accessToken !=null
    //println("isRegistered:$accessToken")
    onResult(isRegistered)
}