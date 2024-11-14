package com.example.tokenschallenge.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun EnterScreen(
    onEnter:()->Unit
){
    LaunchedEffect(Unit) {
        onEnter()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Text(
            text = "Hassen Lite",
            fontSize = 100.sp,
            lineHeight = 200.sp,
            fontWeight = FontWeight.Bold
        )


    }
}