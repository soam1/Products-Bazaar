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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

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
        val sqlQuery = "%${query.replace(' ', '%')}%"
        return db.getProductDao().searchProducts(sqlQuery)
    }


//    suspend fun addProductOffline(product: Product) {
//        product.isSynced = false
//        db.getProductDao().upsert(product)
//    }

    suspend fun getUnsyncedProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            db.getProductDao().getUnsyncedProducts()
        }
    }


    suspend fun syncProductWithServer(product: Product): Response<AddProductResponse> {
        val imagePart = if (product.image.isNotBlank()) {
            val file = File(product.image)
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("image", file.name, requestFile)
        } else {
            null
        }
        return RetrofitInstance.api.addProduct(
            product.product_name.toRequestBody(),
            product.product_type.toRequestBody(),
            product.price.toString().toRequestBody(),
            product.tax.toString().toRequestBody(),
            listOfNotNull(imagePart)
        )
    }


    suspend fun markProductAsSynced(productId: Int) {
        db.getProductDao().markProductAsSynced(productId)
    }
}