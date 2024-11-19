package com.example.tokenschallenge.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokenschallenge.data.dataStore.DataStoreManager
import com.example.tokenschallenge.data.domain.Repository
import com.example.tokenschallenge.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel


@KoinViewModel
class ProfileViewModel(
    private val repository: Repository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    init {
        getProfile()
        }

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    private fun getProfile(
    ) {
        viewModelScope.launch {
            val user = repository.getUserInfo()
            dataStoreManager.saveUserInfoToDataStore(
                user = user?: User.Default
            )
            takeDataToUi(dataStoreManager)
        }
    }
private suspend fun takeDataToUi(
    dataStoreManager: DataStoreManager
)
{
    val userInfo = dataStoreManager.getUSer().first()
    _profileUiState.update { currentState ->
        currentState.copy(
            name = userInfo.payload.user.firstName,
            country = userInfo.payload.user.country,
            id = userInfo.payload.user._id,
            phone = userInfo.payload.user.phone
        )
    }
}

fun onLogout() {
    viewModelScope.launch {
        dataStoreManager.clearDataStore()
    }
}

}
