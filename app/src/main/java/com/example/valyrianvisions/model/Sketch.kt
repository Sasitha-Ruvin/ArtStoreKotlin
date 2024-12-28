package com.example.valyrianvisions.model

import com.example.valyrianvisions.ProductItem

data class Sketch(
    override val imageResourceId: Int,
    override val stringResourceId: Int,
    override val descriptionResourceId: Int,
    override val price: Double
): ProductItem