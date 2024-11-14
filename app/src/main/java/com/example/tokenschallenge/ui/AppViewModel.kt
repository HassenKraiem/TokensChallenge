package com.example.tokenschallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokenschallenge.dataStore.DataStoreManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel (
private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _logState=MutableStateFlow(LogState())
    val logState=_logState.asStateFlow()

    init {
        viewModelScope.launch {
            _logState.update { currentState ->
                currentState.copy(
                    isLoggedIn = dataStoreManager.getIsLoggedIn().first()
                )
            }
        }
    }
    fun onLogout() {
        viewModelScope.launch {
            dataStoreManager.clearDataStore()
            _logState.update { currentState ->
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
                _logState.update { currentState ->
                    currentState.copy(
                        isLoggedIn = true
                    )
                }
        }
    }

}