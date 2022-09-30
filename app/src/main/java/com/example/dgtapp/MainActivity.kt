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

        //brings the database in to MainActivity
        val db by lazy { AppDatabase.getDatabase(this).todoDao() }
        fun insertTodo(todo: ToDo) {
            lifecycleScope.launch {
                db.insertTodo(todo)
            }
        }

        //this function was intended to delete items from the database. I ran into the snag that I need the specific uid of the item in order to delete it (they are randomly generated I believe,)
        //I ran out of time to actually use it
        fun deleteTodo(todo: ToDo) {
            lifecycleScope.launch {
                db.deleteTodo(todo)
            }
        }

        //holds the data for todolist. this is what the recyclerview displays
        var todoList = mutableListOf<ToDo>()

        //gets all titles from database and input them to todoList
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

        //the code for the "+" new item button
        //basically, sets the edittext and submitbutton to visible if they're invisible, and makes the invisible if they are visible
        val ibNewToDoPlus = findViewById<ImageButton>(R.id.ibNewToDoPlus);
        ibNewToDoPlus.setOnClickListener {
            if(!etToDo.isVisible) {
                etToDo.isVisible = true
                ibNewToDoSubmit.isVisible = true
                //forces focus on the edittext
                etToDo.requestFocus()
                //clears the edittext field
                etToDo.text.clear()
                //makes the keyboard come up automagically
                showSoftKeyboard(etToDo)
            }
            else {
                etToDo.isVisible = false
                ibNewToDoSubmit.isVisible = false
                //force removes focus from the edittext (a bit redundant since the keyboard gets hidden)
                etToDo.clearFocus()
                //hides the keyboard
                hideSoftKeyboard(etToDo)
            }
        }

        //tells the recyclerview to draw from the todoList list
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
//this function hides the keyboard
fun Activity.hideSoftKeyboard(editText: EditText){
    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(editText.windowToken, 0)
    }
}

//this function makes the keyboard show
fun Activity.showSoftKeyboard(editText: EditText){
    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        showSoftInput(editText, 0)
    }
}