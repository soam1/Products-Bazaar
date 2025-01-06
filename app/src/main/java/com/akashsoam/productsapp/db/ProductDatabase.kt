package com.akashsoam.productsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.akashsoam.productsapp.models.Product

@Database(
    entities = [Product::class], version = 1
)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao

    companion object {
        @Volatile
        private var instance: ProductDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = androidx.room.Room.databaseBuilder(
            context.applicationContext, ProductDatabase::class.java, "product_db.db"
        ).build()
    }


}