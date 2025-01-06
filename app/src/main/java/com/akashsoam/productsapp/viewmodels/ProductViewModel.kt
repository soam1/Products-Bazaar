package com.akashsoam.productsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akashsoam.productsapp.models.ProductResponse
import com.akashsoam.productsapp.repository.ProductRepository
import com.akashsoam.productsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductViewModel(val productRepository: ProductRepository) : ViewModel() {

    val productsList: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()

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
}
