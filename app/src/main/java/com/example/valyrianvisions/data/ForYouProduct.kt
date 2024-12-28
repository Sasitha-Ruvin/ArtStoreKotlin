package com.example.valyrianvisions.data

import com.google.gson.annotations.SerializedName

data class ForYouProduct(
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double
)
