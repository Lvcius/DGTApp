package com.example.dgtapp

import androidx.room.TypeConverter

class todoConverter {
    @TypeConverter
    //this converts boolean functions into integers. If the value is true, the integer is 1, else it is 0
    fun BooleantoInt(b: Boolean?): Int? {
        var a = if (b == true) 1 else 0
        return a
    }
    //this function does the opposite to the last one
    fun InttoBoolean(b: Int?): Boolean? {
        var a = if (b == 1) true else false
        return a
    }
}

