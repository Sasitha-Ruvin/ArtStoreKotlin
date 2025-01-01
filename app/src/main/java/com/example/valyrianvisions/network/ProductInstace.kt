package com.example.valyrianvisions.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductInstace {
    private const val BASE_URL = "http://10.0.2.2:8000/"

    val api: ProductService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)
    }
}