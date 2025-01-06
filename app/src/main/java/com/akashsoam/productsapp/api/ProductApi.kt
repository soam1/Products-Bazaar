package com.akashsoam.productsapp.api

import com.akashsoam.productsapp.models.ProductResponse
import com.akashsoam.productsapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET


interface ProductApi {
    @GET(Constants.GET_PRODUCTS)
//    suspend fun getProducts(): List<Product>
    suspend fun getProducts(): Response<ProductResponse>


}
