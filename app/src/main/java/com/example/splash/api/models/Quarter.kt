package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class Quarter(@SerializedName("district")
                   val district: District,
                   @SerializedName("display_order")
                   val displayOrder: Int = 0,
                   @SerializedName("name")
                   val name: String = "",
                   @SerializedName("detail")
                   val detail: String = "",
                   @SerializedName("district_id")
                   val districtId: Int = 0,
                   @SerializedName("id")
                   val id: Int = 0,
                   @SerializedName("sort_order")
                   val sortOrder: Int = 0)