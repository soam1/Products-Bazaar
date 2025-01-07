package com.akashsoam.productsapp.repository

import com.akashsoam.productsapp.api.RetrofitInstance
import com.akashsoam.productsapp.db.ProductDatabase
import com.akashsoam.productsapp.models.AddProductResponse
import com.akashsoam.productsapp.models.Product
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class ProductRepository(val db: ProductDatabase) {

    suspend fun getProducts() = RetrofitInstance.api.getProducts()

    suspend fun addProductOnline(
        productName: String,
        productType: String,
        price: String,
        tax: String
    ): Response<AddProductResponse> {
        return RetrofitInstance.api.addProduct(
            productName.toRequestBody(),
            productType.toRequestBody(),
            price.toRequestBody(),
            tax.toRequestBody()
        )
    }

    suspend fun upsert(product: Product) = db.getProductDao().upsert(product)

    fun getSavedProducts() = db.getProductDao().getAllProducts()

    suspend fun deleteProduct(product: Product) = db.getProductDao().deleteProduct(product)


    suspend fun saveProductLocally(product: Product) {
        db.getProductDao().upsert(product)
    }


}