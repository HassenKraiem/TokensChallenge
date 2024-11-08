

package com.example.tokenschallenge.authorization

import kotlinx.serialization.Serializable


@Serializable
data class Tokens(
    val accessToken:String="",
    val refreshToken:String=""
    )