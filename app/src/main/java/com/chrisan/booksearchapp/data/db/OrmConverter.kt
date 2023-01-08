package com.chrisan.booksearchapp.data.db

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class OrmConverter {
    @TypeConverter
    fun fromList(value: List<String>) = Json.encodeToString(value) // List -> String

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value) // String -> List
}