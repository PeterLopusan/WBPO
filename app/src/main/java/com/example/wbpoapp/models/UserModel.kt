package com.example.wbpoapp.models

import com.squareup.moshi.Json

data class UserModel(
    val id: Int?,
    @Json(name = "first_name")
    val firstname: String?,
    @Json(name = "last_name")
    val lastname: String?,
    val email: String?,
    val avatar: String?
)
