package com.simbiri.myquiz.data.local.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class OptionListConverter {
    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun fromStringToList(listString: String): List<String>{
        return Json.decodeFromString(listString)
    }
}