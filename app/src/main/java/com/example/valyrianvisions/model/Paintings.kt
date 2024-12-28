package com.example.valyrianvisions.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.valyrianvisions.ProductItem

data class Paintings(
    override val imageResourceId: Int,
    override val stringResourceId: Int,
    override val descriptionResourceId: Int,
    override val price: Double
) : ProductItem