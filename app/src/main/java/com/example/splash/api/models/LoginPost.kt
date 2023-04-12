package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class LoginPost(
    @SerializedName("password")
    val password: String = "",
    @SerializedName("email")
    val email: String = ""
)