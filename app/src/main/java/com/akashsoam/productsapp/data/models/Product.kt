package com.akashsoam.productsapp.data.models

data class Product(
    val id: Int,
    val productName: String,
    val productType: String,
    val price: Double,
    val tax: Double,
    val imageUrl: String?
)