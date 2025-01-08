package com.akashsoam.productsapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.akashsoam.productsapp.api.RetrofitInstance
import com.akashsoam.productsapp.db.ProductDatabase
import com.akashsoam.productsapp.models.AddProductResponse
import com.akashsoam.productsapp.models.Product
import com.akashsoam.productsapp.models.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class ProductRepository(val db: ProductDatabase) {

//    suspend fun getProducts() = RetrofitInstance.api.getProducts()

    suspend fun getProducts(): Response<ProductResponse> {
        val response = RetrofitInstance.api.getProducts()
        if (response.isSuccessful) {
            response.body()?.let { products ->
                withContext(Dispatchers.IO) {
                    products.forEach { product ->
                        val result = db.getProductDao().upsert(product)
                        Log.d(
                            "ProductRepository",
                            "Upsert result for ${product.product_name}: $result "
                        )
                    }
                }
            }
        } else {
            Log.e("ProductRepository", "Failed to fetch products: ${response.message()}")
        }

        return response
    }


//    suspend fun addProductOnline(
//        productName: String,
//        productType: String,
//        price: String,
//        tax: String
//    ): Response<AddProductResponse> {
//
//        return RetrofitInstance.api.addProduct(
//            productName.toRequestBody(),
//            productType.toRequestBody(),
//            price.toRequestBody(),
//            tax.toRequestBody()
//        )
//    }

    suspend fun addProductWithImages(
        productName: String,
        productType: String,
        price: String,
        tax: String,
        files: List<MultipartBody.Part>
    ): Response<AddProductResponse> {
        return RetrofitInstance.api.addProduct(
            productName.toRequestBody(),
            productType.toRequestBody(),
            price.toRequestBody(),
            tax.toRequestBody(),
            files
        )
    }

    fun getSavedProducts(): LiveData<List<Product>> {
        return db.getProductDao().getAllProducts()
    }

    fun getFilteredProducts(query: String): LiveData<List<Product>> {
        // Modify the query to use SQL's LIKE wildcard before and after the search term
        val sqlQuery = "%${query.replace(' ', '%')}%"
        return db.getProductDao().searchProducts(sqlQuery)
    }

}