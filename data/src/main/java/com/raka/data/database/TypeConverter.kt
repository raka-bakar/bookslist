package com.raka.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 *  A converter class to transfrom List of String to Json string and vice versa
 *  so it can be stored in the database
 */
internal class TypeConverter {

    @TypeConverter
    fun fromJsonToList(json: String): List<String>? {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun fromListStringToJson(list: List<String>): String {
        return Gson().toJson(list)
    }
}