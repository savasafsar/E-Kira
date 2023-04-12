package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("firstName") val firstName: String = "",
    @SerializedName("lastName") val lastName: String = "",
    @SerializedName("createdDate") val createdDate: String = "",
    @SerializedName("validated") val validated: Boolean = false,
    @SerializedName("id") val userId: String = "",
    @SerializedName("email") val email: String = ""
)