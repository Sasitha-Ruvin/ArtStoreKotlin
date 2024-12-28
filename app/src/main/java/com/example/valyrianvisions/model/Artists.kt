package com.example.valyrianvisions.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Artists(
    @DrawableRes val imageResourceId: Int,
    @StringRes val stringResourceId: Int
)
