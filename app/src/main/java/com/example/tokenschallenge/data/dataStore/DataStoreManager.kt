package com.example.tokenschallenge.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tokenschallenge.user.Payload
import com.example.tokenschallenge.user.User
import com.example.tokenschallenge.user.UserX
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

const val USER_DATASTORE = "user_data"

val Context.preferenceData: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)
@Single
class DataStoreManager(@Provided private val context: Context) {
    companion object {
        val USER_ID = stringPreferencesKey("USER_ID")
        val USER_PHONE = stringPreferencesKey("USER_PHONE")
        val USER_FIRST_NAME = stringPreferencesKey("USER_FIRST_NAME")
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        val IS_LOGGED_IN = booleanPreferencesKey("IS_LOGGED_IN")
    }

    suspend fun saveUserInfoToDataStore(user: User) {
        context.preferenceData.edit {
            it[USER_ID] =user.payload.user._id
            it[USER_FIRST_NAME] =user.payload.user.firstName
            it[USER_PHONE] =user.payload.user.phone
        }
    }
    fun getUSer()=context.preferenceData.data.map {
           User.Default.copy(
               payload = Payload(
                   UserX(
                       _id = it[USER_ID]?:"",
                       phone = it[USER_PHONE]?:"",
                       firstName = it[USER_FIRST_NAME]?:""

                   )
               )
           )
    }


    suspend fun saveAccessToken(accessToken: String) {
        context.preferenceData.edit {
            it[ACCESS_TOKEN] = accessToken
                it[IS_LOGGED_IN] = true

        }
    }
    fun getIsLoggedIn()=
        context.preferenceData.data.map {
            it[IS_LOGGED_IN]?:false
        }

     fun getAccessToken()  =
        context.preferenceData.data.map {
            it[ACCESS_TOKEN] ?: ""
        }

    fun getRefreshToken()  =
        context.preferenceData.data.map {
            it[REFRESH_TOKEN] ?: ""
        }


    suspend fun saveRefreshToken(refreshToken: String) {
        context.preferenceData.edit {
            it[REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun clearDataStore() = context.preferenceData.edit {
        it.clear()
        it[IS_LOGGED_IN]=false
    }

}