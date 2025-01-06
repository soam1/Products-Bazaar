package com.akashsoam.productsapp.db

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromArrayList(list: List<String>): String {
        return list.joinToString(",")
    }
}