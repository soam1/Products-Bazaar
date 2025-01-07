package com.akashsoam.productsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akashsoam.productsapp.models.AddProductResponse
import com.akashsoam.productsapp.models.Product
import com.akashsoam.productsapp.models.ProductResponse
import com.akashsoam.productsapp.repository.ProductRepository
import com.akashsoam.productsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductViewModel(val productRepository: ProductRepository) : ViewModel() {

    val productsList: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()

    val productAdditionResult = MutableLiveData<Resource<AddProductResponse>>()

    val searchQuery = MutableLiveData<String>()


    init {
        getProductsList()
    }

    fun getProductsList() = viewModelScope.launch {
        productsList.postValue(Resource.Loading())
        val response = productRepository.getProducts()
        productsList.postValue(handleProductResponse(response))
    }

    private fun handleProductResponse(response: Response<ProductResponse>): Resource<ProductResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

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



}
