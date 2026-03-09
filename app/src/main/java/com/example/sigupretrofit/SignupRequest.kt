package com.example.sigupretrofit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignupRequest(
    @Json(name = "name")     val name: String,
    @Json(name = "email")    val email: String,
    @Json(name = "password") val password: String
)