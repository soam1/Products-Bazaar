package com.akashsoam.productsapp.ui.frags

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akashsoam.productsapp.data.models.Product

class ProductViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    fun searchProducts(query: String) {
        // Implement search logic here
    }

    fun addProduct(product: Product) {
        // Implement add product logic here
    }
}
