package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class Town(@SerializedName("city")
                val city: City,
                @SerializedName("display_order")
                val displayOrder: Int = 0,
                @SerializedName("name")
                val name: String = "",
                @SerializedName("id")
                val id: Int = 0,
                @SerializedName("sort_order")
                val sortOrder: Int = 0,
                @SerializedName("city_id")
                val cityId: Int = 0)