package com.example.tokenschallenge.user

import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    val user: UserX=UserX()
)