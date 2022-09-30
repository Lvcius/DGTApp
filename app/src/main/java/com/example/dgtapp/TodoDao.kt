package com.example.dgtapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {

    //this function gets a list of all entities in the database
    @Query("SELECT * FROM ToDo")
    fun getAll(): List<ToDo>

    //this adds new items
    @Insert
    fun insertTodo(todo: ToDo)

    //this removes items
    @Delete
    fun deleteTodo(todo: ToDo)
}