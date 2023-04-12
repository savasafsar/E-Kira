package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class LoginRes(
    @SerializedName("accessToken") val accessToken: String = "",
    @SerializedName("user") val user: User
)