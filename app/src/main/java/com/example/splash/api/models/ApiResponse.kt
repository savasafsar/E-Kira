package com.example.splash.api.models

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class ApiResponse<T>(
    @SerializedName("result") val result: T,
    @SerializedName("headers") val headers: JSONObject?,
    @SerializedName("error") val error: String = "",
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("statusCode") val statusCode: Int = 0)