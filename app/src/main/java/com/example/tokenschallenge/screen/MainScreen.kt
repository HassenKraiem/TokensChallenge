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
import com.example.tokenschallenge.MainActivity
import com.example.tokenschallenge.dataStore.DataStoreManager
import com.example.tokenschallenge.dataStore.DataStoreManager.Companion.ACCESS_TOKEN
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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
        checkRegisterState(preferenceDataStore) {
            isRegistered = it
        }
    }
    if (isRegistered) {
        ProfileScreen(dataStoreManager, onLogout)
    } else {
        LogScreen(
            onRegisterSuccess = { isRegistered = true },
            dataStoreManager = dataStoreManager,
        )
    }


}

suspend fun checkRegisterState(
    preferenceDataStore: DataStore<Preferences>, onResult: (Boolean) -> Unit
) {
    val preferences = preferenceDataStore.data.first()
    val accessToken = preferences[ACCESS_TOKEN]
    val isRegistered = accessToken != null
    //println("isRegistered:$accessToken")
    onResult(isRegistered)
}