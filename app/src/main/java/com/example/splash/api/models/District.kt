package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class District(@SerializedName("town")
                    val town: Town,
                    @SerializedName("town_id")
                    val townId: Int = 0,
                    @SerializedName("display_order")
                    val displayOrder: Int = 0,
                    @SerializedName("name")
                    val name: String = "",
                    @SerializedName("id")
                    val id: Int = 0,
                    @SerializedName("sort_order")
                    val sortOrder: Int = 0)