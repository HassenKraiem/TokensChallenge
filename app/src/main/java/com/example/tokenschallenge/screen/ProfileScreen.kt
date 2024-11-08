package com.example.tokenschallenge.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tokenschallenge.UltimateClass
import com.example.tokenschallenge.dataStore.DataStoreManager
import com.example.tokenschallenge.ui.AppViewModel
import kotlinx.coroutines.Job

@Composable
fun ProfileScreen(
    /*token:String?,
        status:String
        appViewModel: AppViewModel,*/
    dataStoreManager: DataStoreManager,
    onLogout: () -> Job
) {
   // val uiState by appViewModel.uiState.collectAsState()
    val userDetails by dataStoreManager.getFromDataStore().collectAsState(initial = null)
    Column {
        /*Text(
            text = uiState.tokenInfo?.accessToken.orEmpty(),
            fontSize = 39.sp
        )*/
        Text(
            text = "Hi, ${"\nWelcome to your Profile "}",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )
        Text(
            text = "Name: ${userDetails?.user?.payload?.user?.firstName ?: "hassen"}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Mobile: ${userDetails?.user?.payload?.user?.phone ?: ""}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Id: ${userDetails?.user?.payload?.user?._id ?: ""}",
            style = MaterialTheme.typography.headlineSmall
        )



        Button(
            onClick = { onLogout() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Logout",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )
        }
        /*Text(
            text = "FirstName:${uiState.userInfo?.payload?.user?.firstName}",
            fontSize = 40.sp,
            lineHeight = 70.sp
        )
        Text(
            text = "LastName:${uiState.userInfo?.payload?.user?.lastName}",
            fontSize = 40.sp,
            lineHeight = 70.sp
        )
        Text(
            text = "ID:${uiState.userInfo?.payload?.user?._id}".orEmpty(),
            fontSize = 40.sp,
            lineHeight = 70.sp
        )
        Text(
            text = "Country:${uiState.userInfo?.payload?.user?.country}".orEmpty(),
            fontSize = 40.sp,
            lineHeight = 70.sp
        )
        Text(
            text = "Status:${uiState.userInfo?.status}".orEmpty(),
            fontSize = 45.sp,
            lineHeight = 70.sp
        )
        Text(
            text = "Phone:${uiState.userInfo?.payload?.user?.phone}"
        )*/

    }
}