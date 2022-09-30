package com.example.dgtapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    //unique id of each item, doesn't generate in any particualar order, and i don't know how to make it so this is a big headache
    @PrimaryKey(autoGenerate = true) var uid: Int,
    //text of the todo item
    var title: String,
    //wether the item is checked or not
    var isChecked: Boolean,
) {
    //I ran into trouble with the @ignore parameter which is supposed to make the database ignore the variable, but it bugged out and all I could find online said it was an unfixed bug.
    //putting it here into a class body allowed the app to build, but now nothing can actually access the variable
    @Ignore var isCheckedToDel: Boolean = false
}