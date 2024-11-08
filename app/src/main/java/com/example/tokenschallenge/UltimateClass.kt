package com.example.tokenschallenge

import com.example.tokenschallenge.authorization.Tokens
import com.example.tokenschallenge.user.User
import kotlinx.serialization.Serializable

@Serializable
data class UltimateClass(
    val user:User,
    val tokens: Tokens=Tokens()
)