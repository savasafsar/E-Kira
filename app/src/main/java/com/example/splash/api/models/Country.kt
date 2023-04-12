package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class Country(@SerializedName("display_order")
                   val displayOrder: Int = 0,
                   @SerializedName("name")
                   val name: String = "",
                   @SerializedName("language")
                   val language: String = "",
                   @SerializedName("id")
                   val id: Int = 0,
                   @SerializedName("abbreviation")
                   val abbreviation: String = "",
                   @SerializedName("alpha3_code")
                   val alphaCode3: String = "",
                   @SerializedName("sort_order")
                   val sortOrder: Int = 0,
                   @SerializedName("alpha2_code")
                   val alphaCode2: String = "",
                   @SerializedName("phone_code")
                   val phoneCode: String = "")