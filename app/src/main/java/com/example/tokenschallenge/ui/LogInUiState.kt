package com.example.tokenschallenge.ui

data class LogInUiState(
    val isLoggedIn:Boolean=false,
    val errorMessage:String="",
    val name:String="",
    val number:String="",
    val country:String="",
    val id:String=""
)
