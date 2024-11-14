package com.example.tokenschallenge.ui

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokenschallenge.dataStore.DataStoreManager
import com.example.tokenschallenge.dataStore.DataStoreManager.Companion.ACCESS_TOKEN
import com.example.tokenschallenge.domain.Repository
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
    suspend fun checkRegisterState(
        preferenceDataStore: DataStore<Preferences>
    ) {
        val preferences = preferenceDataStore.data.first()
        val accessToken = preferences[ACCESS_TOKEN]
        val isRegistered = accessToken != null
        println("isRegistered:$isRegistered")
        _logState.update { currentState ->
            currentState.copy(
                isLoggedIn = isRegistered
            )
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
        _logState.update { currentState ->
            currentState.copy(
                isLoggedIn = true
            )
        }
    }

}