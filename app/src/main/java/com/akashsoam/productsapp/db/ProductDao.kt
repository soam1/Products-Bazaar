package com.akashsoam.productsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akashsoam.productsapp.models.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(product: Product): Long

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>

    @Delete
    fun deleteProduct(product: Product)


    @Query("SELECT * FROM products WHERE product_name LIKE :searchQuery OR product_type LIKE :searchQuery")
    fun searchProducts(searchQuery: String): LiveData<List<Product>>

    @Query("SELECT * FROM products WHERE isSynced = 0")
    fun getUnsyncedProducts(): List<Product>

    @Query("UPDATE products SET isSynced = 1 WHERE id = :productId")
    suspend fun markProductAsSynced(productId: Int)
}