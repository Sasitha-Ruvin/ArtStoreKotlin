package com.example.valyrianvisions.network

import retrofit2.http.GET

interface ProductService {
    @GET("api/products")
    suspend fun getProducts(): ApiResponse
}