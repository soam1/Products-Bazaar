package com.akashsoam.productsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akashsoam.productsapp.models.Product
import com.akashsoam.productsapp.models.ProductResponse
import com.akashsoam.productsapp.repository.ProductRepository
import com.akashsoam.productsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductViewModel(val productRepository: ProductRepository) : ViewModel() {

    val productsList: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    val filteredProductsList: MutableLiveData<Resource<List<Product>>> = MutableLiveData()


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


    fun filterProducts(query: String) {
//        val currentProducts =productsList.value?.data?.products  // Assuming ProductResponse has a list of products named "products"
        val currentProducts =
            productsList.value?.data // Assuming ProductResponse has a list of products named "products"
        if (currentProducts != null) {
            if (query.isEmpty()) {
                filteredProductsList.postValue(Resource.Success(currentProducts))
            } else {
                val filtered = currentProducts.filter {
                    it.product_name.contains(query, ignoreCase = true) ||
                            it.product_type.contains(query, ignoreCase = true)
                }
                filteredProductsList.postValue(Resource.Success(filtered))
            }
        } else {
            // Handle case where currentProducts is null or there's an error
            filteredProductsList.postValue(Resource.Error("No products available or invalid query"))
        }
    }
}
