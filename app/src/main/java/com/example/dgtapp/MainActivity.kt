package com.example.dgtapp

import android.os.Bundle
import android.view.View
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
        val textView = findViewById<View>(R.id.text_date_display) as TextView
        textView.text = formattedDate
    }
}