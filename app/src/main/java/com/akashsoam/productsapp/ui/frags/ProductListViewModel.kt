package com.akashsoam.productsapp.ui.frags

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akashsoam.productsapp.data.models.Product

class ProductListViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products
}