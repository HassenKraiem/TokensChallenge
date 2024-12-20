package com.example.tokenschallenge.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tokenschallenge.data.dataStore.DataStoreManager
import com.example.tokenschallenge.navigation.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided


@KoinViewModel
class AppViewModel (
@Provided private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _appState=MutableStateFlow(AppState())

    init {
        viewModelScope.launch {
            _appState.update { currentState ->
                currentState.copy(
                    isLoggedIn = dataStoreManager.getIsLoggedIn().first()
                )
            }
        }
    }
    fun onLogout(navController: NavController) {
        viewModelScope.launch {
            dataStoreManager.clearDataStore()
            _appState.update { currentState ->
                currentState.copy(
                    isLoggedIn = false
                )
            }
            navController.navigate(Route.LogInScreen)


        }
    }
    fun onLoggedIn(navController: NavController) {
        viewModelScope.launch {
            delay(2000L)
            if (dataStoreManager.getAccessToken().first()!="") {
                _appState.update { currentState ->
                    currentState.copy(
                        isLoggedIn = true
                    )
                }
                navController.navigate(Route.ProfileScreen)
            }
        }
    }
    fun onEnter(navController: NavController){
        viewModelScope.launch {
            delay(2000L)
            navController.navigate(if (_appState.value.isLoggedIn) Route.ProfileScreen
                else Route.LogInScreen)
        }
    }

}