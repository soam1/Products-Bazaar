package com.akashsoam.productsapp.api

import com.akashsoam.productsapp.models.AddProductResponse
import com.akashsoam.productsapp.models.ProductResponse
import com.akashsoam.productsapp.util.Constants
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface ProductApi {
    @GET(Constants.GET_PRODUCTS)
//    suspend fun getProducts(): List<Product>
    suspend fun getProducts(): Response<ProductResponse>

    @Multipart
    @POST("add")
    suspend fun addProduct(
        @Part("product_name") productName: RequestBody,
        @Part("product_type") productType: RequestBody,
        @Part("price") price: RequestBody,
        @Part("tax") tax: RequestBody
    ): Response<AddProductResponse>

}
