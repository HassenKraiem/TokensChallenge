package com.example.tokenschallenge.user

import kotlinx.serialization.Serializable

@Serializable
data class CountryData(
    val ISOCode: String="",
    val __v: Int=0,
    val _id: String="",
    val code: String="",
    val createdAt: String="",
    val first: String="",
    val flag: String="",
    val isEmailRegistrationAvailable: Boolean=false,
    val isPhoneRegistrationAvailable: Boolean=false,
    val name: String="",
    val updatedAt: String=""
)