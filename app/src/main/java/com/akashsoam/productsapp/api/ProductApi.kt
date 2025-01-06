package com.akashsoam.productsapp.api

import com.akashsoam.productsapp.ProductResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ProductApi {
    @GET("get")
//    suspend fun getProducts(): List<Product>
    suspend fun getProducts(): ProductResponse

    companion object {
        fun create(): ProductApi {
            return Retrofit.Builder()
                .baseUrl("https://app.getswipe.in/api/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ProductApi::class.java)
        }
    }
}
