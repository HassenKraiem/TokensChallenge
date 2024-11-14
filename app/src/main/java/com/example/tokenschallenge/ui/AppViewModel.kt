package com.example.tokenschallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tokenschallenge.dataStore.DataStoreManager
import com.example.tokenschallenge.navigation.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel (
private val dataStoreManager: DataStoreManager
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
    fun onLogout() {
        viewModelScope.launch {
            dataStoreManager.clearDataStore()
            _appState.update { currentState ->
                currentState.copy(
                    isLoggedIn = false
                )
            }

        }
    }
    fun onLoggedIn() {
        viewModelScope.launch {
            delay(1000L)
            if (dataStoreManager.getAccessToken().first()!="")
                _appState.update { currentState ->
                    currentState.copy(
                        isLoggedIn = true
                    )
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