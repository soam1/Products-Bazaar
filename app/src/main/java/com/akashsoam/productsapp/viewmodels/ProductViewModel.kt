package com.akashsoam.productsapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_MOBILE
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akashsoam.productsapp.ProductApplication
import com.akashsoam.productsapp.models.AddProductResponse
import com.akashsoam.productsapp.models.Product
import com.akashsoam.productsapp.models.ProductResponse
import com.akashsoam.productsapp.repository.ProductRepository
import com.akashsoam.productsapp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

class ProductViewModel(val productRepository: ProductRepository, application: Application) :
    AndroidViewModel(
        application
    ) {

    val productsList: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()

    val productAdditionResult = MutableLiveData<Resource<AddProductResponse>>()

    private val searchQuery = MutableStateFlow("")

    init {
        getProductsList()
    }

    fun getProductsList() = viewModelScope.launch {
//        productsList.postValue(Resource.Loading())
//        val response = productRepository.getProducts()
//        productsList.postValue(handleProductResponse(response))
        safeGetProductsListCall()
    }


    private fun handleProductResponse(response: Response<ProductResponse>): Resource<ProductResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

//    fun getProductsList() = viewModelScope.launch {
//        productsList.postValue(Resource.Loading())
//        val response = productRepository.getProducts()
//        if (response.isSuccessful) {
//            response.body()?.let { responseBody ->
//                productsList.postValue(Resource.Success(responseBody))
//            } ?: productsList.postValue(Resource.Error("Received null response from server"))
//        } else {
//            productsList.postValue(Resource.Error(response.message() ?: "Unknown error"))
//        }
//    }


    fun addProduct(product: Product) {
        viewModelScope.launch {
            productAdditionResult.postValue(Resource.Loading())
            try {
                val response = productRepository.addProductOnline(
                    product.product_name,
                    product.product_type,
                    product.price.toString(),
                    product.tax.toString()
                )
                if (response.isSuccessful) {
                    productAdditionResult.postValue(Resource.Success(response.body()!!))
                    getProductsList()
                } else {
                    productAdditionResult.postValue(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                productAdditionResult.postValue(Resource.Error(e.toString()))
            }
        }
    }


    private suspend fun safeGetProductsListCall() {
        return try {
            if (hasInternetConnection()) {
                val response = productRepository.getProducts()
                productsList.postValue(handleProductResponse(response))
            } else {
                productsList.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> productsList.postValue(Resource.Error("Network Failure"))
                else -> productsList.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<ProductApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
//        return connectivityManager.activeNetworkInfo.isConnected
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false

            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }


    fun setQuery(query: String) {
        searchQuery.value = query
    }
}
