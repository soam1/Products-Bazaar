package com.akashsoam.productsapp.repository

import com.akashsoam.productsapp.api.RetrofitInstance
import com.akashsoam.productsapp.db.ProductDatabase

class ProductRepository(val db: ProductDatabase) {

    suspend fun getProducts() = RetrofitInstance.api.getProducts()

}