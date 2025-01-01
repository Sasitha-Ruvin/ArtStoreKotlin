package com.example.valyrianvisions.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val image_path: String,
    val category: Category,
    val created_at: String,
    val updated_at: String
)
