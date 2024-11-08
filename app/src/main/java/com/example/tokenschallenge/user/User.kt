package com.example.tokenschallenge.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val code: Int,
    val payload: Payload,
    val status: String
){
    companion object{
        val Default = User(
            code = 200,
            payload = Payload(),
            status = ""
        )
    }
}