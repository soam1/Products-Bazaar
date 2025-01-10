package com.akashsoam.productsapp.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "products", indices = [Index(value = ["product_name"], unique = true)])

data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val image: String,
    val price: Double,
    val product_name: String,
    val product_type: String,
    val tax: Int,
    var isSynced: Boolean = false
)