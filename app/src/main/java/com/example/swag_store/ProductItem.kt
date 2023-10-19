package com.example.swag_store
import androidx.annotation.DrawableRes

data class ProductItem(
    val title: String,
    val price: Double,
    @DrawableRes val image: Int,
    val description: String
)

data class Products(val items: List<ProductItem>)