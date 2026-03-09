package com.example.sigupretrofit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignupResponse(
    @Json(name = "id")       val id: Int,
    @Json(name = "name")     val name: String,
    @Json(name = "email")    val email: String
)