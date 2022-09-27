package com.example.dgtapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // gets time from Calendar and formats it
        val c: Calendar = Calendar.getInstance()
        val df = SimpleDateFormat("MMM d yyyy")
        val formattedDate: String = df.format(c.time)
        // sets the text in the TextView to the date (using the format above)
        val textView = findViewById<View>(R.id.tvDate) as TextView
        textView.text = formattedDate

        //show/hide edittext and submit button for adding new todo item
        val etToDo = findViewById<>(R.id.etToDo);
        etToDo.isvisible = false

        fun showHide(view:View) {
            view.visibility = if (view.visibility == View.VISIBLE){
                View.INVISIBLE
            } else{
                View.VISIBLE
            }
        }


    }
}