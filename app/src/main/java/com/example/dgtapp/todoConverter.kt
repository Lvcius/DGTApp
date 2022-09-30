package com.example.dgtapp

import androidx.room.TypeConverter

class todoConverter {
    @TypeConverter
    fun BooleantoInt(b: Boolean?): Int? {
        var a = if (b == true) 1 else 0
        return a
    }
    fun InttoBoolean(b: Int?): Boolean? {
        var a = if (b == 1) true else false
        return a
    }
}