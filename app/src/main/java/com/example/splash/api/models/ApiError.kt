package com.example.splash.api.models

import com.google.gson.annotations.SerializedName;

data class ApiError(
        @SerializedName("error") val error: String = "",
        @SerializedName("isSuccess") val isSuccess: Boolean = false,
        @SerializedName("statusCode") val statusCode: Int = 0
)