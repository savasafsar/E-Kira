package com.example.splash

import com.google.gson.annotations.SerializedName
import java.io.Serializable
data class Advent(
     val Aciklama: String,
     val Baslik: String,
     val Kirafiyati: String,
     val Periyot: String,
     val cities: String,
     val districts: String,
     val downloadUrl: String,
     val quarters: String,
     val towns: String


             ) : Serializable {
}