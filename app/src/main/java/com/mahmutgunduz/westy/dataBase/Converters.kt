package com.mahmutgunduz.westy.dataBase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mahmutgunduz.westy.data.model.Rating

/**
 * Room veritabanı için tip dönüştürücüler
 * Kompleks veri tiplerini JSON formatına dönüştürür ve geri çevirir
 */
class Converters {
    // Gson nesnesi, JSON dönüşümleri için kullanılır
    private val gson = Gson()

    /**
     * Rating nesnesini JSON string'e dönüştürür
     * @param rating Dönüştürülecek Rating nesnesi
     * @return JSON formatında string
     */
    @TypeConverter
    fun fromRating(rating: Rating?): String? {
        return if (rating == null) null else gson.toJson(rating)
    }

    /**
     * JSON string'i Rating nesnesine dönüştürür
     * @param ratingString JSON formatında string
     * @return Rating nesnesi veya hata durumunda varsayılan değer
     */
    @TypeConverter
    fun toRating(ratingString: String?): Rating? {
        if (ratingString == null) return null
        val type = object : TypeToken<Rating>() {}.type
        return gson.fromJson(ratingString, type)
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return if (value == null) null else gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        if (value == null) return null
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
} 