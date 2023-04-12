package com.example.splash.api.models

import com.google.gson.annotations.SerializedName;

data class AuthCheckRes (
    @SerializedName("firstName") val firstName: String = "",
    @SerializedName("lastName") val lastName: String = "",
    @SerializedName("validated") val validated: Boolean = false,
    @SerializedName("id") val userId: String = "",
    @SerializedName("email") val email: String = ""
)