package com.example.tokenschallenge.data.dataStore.api

import kotlinx.serialization.Serializable


@Serializable
data class Tokens(
    val accessToken: String = "",
    val refreshToken: String = ""
)