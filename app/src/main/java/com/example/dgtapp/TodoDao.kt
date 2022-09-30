package com.example.dgtapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Query("SELECT * FROM ToDo")
    fun getAll(): LiveData<List<ToDo>>

    @Insert
    fun insertTodo(todo: ToDo)

    @Delete
    fun deleteTodo(todo: ToDo)
}