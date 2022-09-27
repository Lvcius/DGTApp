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
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        ibNewToDoSubmit.isVisible = false

        //show input field and submit button when you press "+" (new todo item)
        val ibNewToDoPlus = findViewById<ImageButton>(R.id.ibNewToDoPlus);
        ibNewToDoPlus.setOnClickListener {
            etToDo.isVisible = true
            ibNewToDoSubmit.isVisible = true
            ibNewToDoPlus.isVisible = false
            //force focus on the input field when button pressed
            etToDo.requestFocus()
        }

        //holds the data of all list items (does not save, will link to database later)
        var todoList = mutableListOf(
            //requires an initial placeholder, first value is text, second is checkbox true/false
            ToDo("placeholder", true)
        )

        //weird adapter stuff???
        val adapter = ToDoAdapter(todoList)
        binding.rvToDo.adapter = adapter
        binding.rvToDo.layoutManager = LinearLayoutManager(this)

        //on pressing the submit button on a new list item: update the recyclerview, hide the input field and submit button, make the "+" button visible again
        binding.ibNewToDoSubmit.setOnClickListener {
            val title = etToDo.text.toString()
            val todo = ToDo(title, false)
            todoList.add(todo)
            adapter.notifyItemInserted(todoList.size - 1)
            etToDo.isVisible = false
            ibNewToDoSubmit.isVisible = false
            ibNewToDoPlus.isVisible = true
            // Clear text
            etToDo.text.clear()
            //hide keyboard
            hidekeyboard()
        }


    }
}