package com.akashsoam.productsapp.util.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.akashsoam.productsapp.repository.ProductRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SyncProductsWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams), KoinComponent {
    private val productRepository: ProductRepository by inject()

    override suspend fun doWork(): Result {
        try {
            val unsyncedProducts = productRepository.getUnsyncedProducts()
            unsyncedProducts.forEach { product ->
                val response = productRepository.syncProductWithServer(product)
                if (response.isSuccessful) {
                    productRepository.markProductAsSynced(product.id!!)
                } else {
                    throw Exception("Failed to sync product: ${product.id}")
                }
            }
            return Result.success()
        } catch (e: Exception) {
            return Result.retry()
        }
    }
}
