package com.example.tokenschallenge.ui

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

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    private fun getUserInfoToDataStore(
    ) {
        viewModelScope.launch {
            val user = repository.getUserInfo()
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
        _profileUiState.update { currentState ->
            currentState.copy(
                name = dataStoreManager.getAccessToken().first(),
                country = dataStoreManager.getRefreshToken().first()
            )
        }
    }

}
