package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class City(@SerializedName("country")
                val country: Country,
                @SerializedName("display_order")
                val displayOrder: Int = 0,
                @SerializedName("name")
                val name: String = "",
                @SerializedName("id")
                val id: Int = 0,
                @SerializedName("tag")
                val tag: String = "",
                @SerializedName("sort_order")
                val sortOrder: Int = 0,
                @SerializedName("country_id")
                val countryId: Int = 0)