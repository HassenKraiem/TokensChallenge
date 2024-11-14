package com.example.tokenschallenge.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokenschallenge.UltimateClass
import com.example.tokenschallenge.dataStore.DataStoreManager
import com.example.tokenschallenge.domain.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: Repository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    init {
        getUserInfoToDataStore()
        }

    private val _profile_uiState = MutableStateFlow(LogInUiState())
    val logInUiState: StateFlow<LogInUiState> = _profile_uiState.asStateFlow()

    private fun getUserInfoToDataStore(
    ) {
        viewModelScope.launch {
            val user = repository.getUserInfo(dataStoreManager)
            if (user != null) {
                dataStoreManager.saveToDataStore(
                    UltimateClass(
                        user = user,
                    )
                )
            }
            takeDataToUi()
        }
    }


    private suspend fun takeDataToUi() {
        _profile_uiState.update { currentState ->
            currentState.copy(
                name = dataStoreManager.getAccessToken().first(),
                country = dataStoreManager.getRefreshToken().first()
            )
        }
    }

}
