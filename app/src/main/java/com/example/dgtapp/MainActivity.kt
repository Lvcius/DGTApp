package com.example.dgtapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dgtapp.databinding.ActivityMainBinding
import java.io.File
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //holds the filtered data for todolist
        var todoList = mutableListOf<ToDo>()

        //holds all data for todolist
        var todoListSaved = mutableListOf<ToDo>()

        // function for writing to save file
        fun write() {
            val writer = PrintWriter("DGTApp/resources/todos.txt")  // java.io.PrintWriter
            for (ToDo in todoListSaved) {
                writer.append("$ToDo")
            }
            writer.close()
        }

        // gets time from Calendar and formats it
        val c: Calendar = Calendar.getInstance()
        val df = SimpleDateFormat("MMM d yyyy")
        val formattedDate: String = df.format(c.time)
        // sets the text in the TextView to the date (using the format above)
        val textView = findViewById<View>(R.id.tvDate) as TextView
        textView.text = formattedDate

        //show/hide edittext and submit button for adding new todo item
        val etToDo = findViewById<EditText>(R.id.etToDo);
        val ibNewToDoSubmit = findViewById<ImageButton>(R.id.ibNewToDoSubmit);
        etToDo.isVisible = false
        //remove focus on edittext
        etToDo.clearFocus()
        ibNewToDoSubmit.isVisible = false

        //show input field and submit button when you press "+" (new todo item)
        val ibNewToDoPlus = findViewById<ImageButton>(R.id.ibNewToDoPlus);
        ibNewToDoPlus.setOnClickListener {
            etToDo.isVisible = true
            ibNewToDoSubmit.isVisible = true
            //force focus on the input field when button pressed
            etToDo.requestFocus()
        }

        //weird adapter stuff???
        val adapter = ToDoAdapter(todoList)
        binding.rvToDo.adapter = adapter
        binding.rvToDo.layoutManager = LinearLayoutManager(this)

        //on pressing the submit button on a new list item: update the recyclerview, hide the input field and submit button, make the "+" button visible again
        binding.ibNewToDoSubmit.setOnClickListener {
            val title = etToDo.text.toString()
            val todo = ToDo(formattedDate, title, false)
            todoList.add(0, todo)
            todoListSaved.add(0, todo)
            adapter.notifyItemInserted(0)
            etToDo.isVisible = false
            ibNewToDoSubmit.isVisible = false
            // Clear text
            etToDo.text.clear()
            //remove focus on edittext when item is submitted
            etToDo.clearFocus()
        }


    }
}