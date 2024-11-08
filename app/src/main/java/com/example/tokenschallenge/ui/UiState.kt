package com.example.tokenschallenge.ui

import com.example.tokenschallenge.authorization.Tokens
import com.example.tokenschallenge.user.User


data class UiState(
    val tokenInfo: Tokens?=Tokens("",""),
    val userInfo: User?=null
)
