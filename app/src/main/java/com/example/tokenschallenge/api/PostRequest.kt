package com.example.tokenschallenge.api

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val phone: String,
    val password: String
)


