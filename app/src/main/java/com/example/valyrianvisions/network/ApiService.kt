package com.example.valyrianvisions.network

import com.example.valyrianvisions.data.ForYouProduct
import com.example.valyrianvisions.data.LatestProduct
import retrofit2.http.GET

interface ApiService {
    @GET("latest_products.json")
    suspend fun getLatestProducts(): List<LatestProduct>
    @GET("for_you_products.json")
    suspend fun getForYouProducts(): List<ForYouProduct>
}