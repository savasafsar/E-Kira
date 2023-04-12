package com.example.splash.api.models

data class IlanModel(
    val ilanBaslik: String,
    val ilanKonum: String,
    val ilanGorseli: Int, // uri den resim nasıl çekilir bakılacak.
    val ilanFiyat: String,
    val ilanPeriyot: String

) {
}