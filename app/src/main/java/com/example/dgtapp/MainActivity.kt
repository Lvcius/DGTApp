package com.example.dgtapp

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dgtapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val db by lazy { AppDatabase.getDatabase(this).todoDao() }
        fun insertTodo(todo: ToDo) {
            lifecycleScope.launch {
                db.insertTodo(todo)
            }
        }

        fun deleteTodo(todo: ToDo) {
            lifecycleScope.launch {
                db.deleteTodo(todo)
            }
        }

        //holds the filtered data for todolist
        var todoList = mutableListOf<ToDo>()

        //get all titles from database and input them to todoList
        var todoListSaved = db.getAll()
        for (ToDo in todoListSaved) {
            todoList.add(ToDo)
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
            if(!etToDo.isVisible) {
                etToDo.isVisible = true
                ibNewToDoSubmit.isVisible = true
                etToDo.requestFocus()
                etToDo.text.clear()
                showSoftKeyboard(etToDo)
            }
            else {
                etToDo.isVisible = false
                ibNewToDoSubmit.isVisible = false
                hideSoftKeyboard(etToDo)
            }
        }

        //weird adapter stuff???
        val adapter = ToDoAdapter(todoList)
        val rvToDo = findViewById<RecyclerView>(R.id.rvToDo)
        rvToDo.adapter = adapter
        rvToDo.layoutManager = LinearLayoutManager(this)

        //on pressing the submit button on a new list item: update the recyclerview and database, hide the input field and submit button, make the "+" button visible again
        ibNewToDoSubmit.setOnClickListener {
            val title = etToDo.text.toString()
            if (title != "") {
                val todo = ToDo(0, title = title, isChecked = false)
                //update recyclerview list
                todoList.add(0, todo)
                //update database
                insertTodo(todo)
                adapter.notifyItemInserted(0)
                //scroll to top
                rvToDo.scrollToPosition(0)
            }

            //remove focus on edittext when item is submitted and hide the interface & keyboard
            etToDo.clearFocus()
            etToDo.isVisible = false
            ibNewToDoSubmit.isVisible = false
            hideSoftKeyboard(etToDo)
        }


    }
}

fun Activity.hideSoftKeyboard(editText: EditText){
    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(editText.windowToken, 0)
    }
}

fun Activity.showSoftKeyboard(editText: EditText){
    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        showSoftInput(editText, 0)
    }
}