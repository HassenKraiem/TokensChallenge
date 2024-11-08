package com.example.tokenschallenge.authorization

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val phone: String,
    val password: String
)


