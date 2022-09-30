package com.example.dgtapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    var title: String,
    var isChecked: Boolean,
) {
    @Ignore var isCheckedToDel: Boolean = false
}