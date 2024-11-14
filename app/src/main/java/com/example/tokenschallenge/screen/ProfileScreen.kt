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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tokenschallenge.ui.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel, logOut: () -> Unit
) {
    val uiState by profileViewModel.profileUiState.collectAsState()
    Column {
        Text(
            text = "Hi, ${"\nWelcome to your Profile "}",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )
        Text(
            text = "Name:${uiState.name}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Mobile: ${uiState.number}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Id: ${uiState.country}", style = MaterialTheme.typography.headlineSmall
        )
        Button(
            onClick = { logOut() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Logout", color = Color.White, style = MaterialTheme.typography.labelLarge
            )
        }

    }
}