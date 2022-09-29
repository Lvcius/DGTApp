package com.example.dgtapp

data class ToDo(
    val duedate: String,
    val title: String,
    var isChecked: Boolean
)