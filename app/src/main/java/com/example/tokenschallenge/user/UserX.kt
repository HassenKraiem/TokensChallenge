package com.example.tokenschallenge.user

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class UserX(
    val _id: String="",
    val avatar: String="",
    val country: String="",
    val countryCode: String="",
    val countryData: CountryData=CountryData(),
    val currentStreak: Int=0,
    val dayEndsAfter: Int=0,
    val firstName: String="",
    val inviteLink: String="",
    val keywordsCount: Int=0,
    val lastName: String="",
    val learningDuration: Int=0,
    val maxStreak: Int=0,
    val phone: String="",
    val progress:List<@Contextual Any> = listOf(),
    val verified: Boolean=false
)