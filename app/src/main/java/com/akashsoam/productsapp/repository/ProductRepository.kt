package com.akashsoam.productsapp.repository

import com.akashsoam.productsapp.api.RetrofitInstance
import com.akashsoam.productsapp.db.ProductDatabase
import com.akashsoam.productsapp.models.Product

class ProductRepository(val db: ProductDatabase) {

    suspend fun getProducts() = RetrofitInstance.api.getProducts()


    suspend fun upsert(product: Product) = db.getProductDao().upsert(product)

    fun getSavedProducts() = db.getProductDao().getAllProducts()

    suspend fun deleteProduct(product: Product) = db.getProductDao().deleteProduct(product)

}