package com.example.tokenschallenge.screen.login

data class LogInUiState(
    val errorMessage:String="",
    val password:String="",
    val phone:String="",
    val loggedIn:Boolean=false
)
