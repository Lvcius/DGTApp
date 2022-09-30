package com.example.dgtapp

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    var title: String,
    var isChecked: Boolean
)