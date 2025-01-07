package com.akashsoam.productsapp.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akashsoam.productsapp.repository.ProductRepository

class ProductViewModelProviderFactory(
    val productRepository: ProductRepository,
    val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(productRepository, application) as T
    }
}