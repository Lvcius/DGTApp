package com.example.dgtapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dgtapp.databinding.ItemTodoBinding

class ToDoAdapter(
    var todos: List<ToDo>
) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    inner class ToDoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    //this function is run when the recyclerview is first created, it sets up bindings and does some inflation stuff? I'm not actually entirely sure what all the inflations are doing.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(layoutInflater, parent, false)
        return ToDoViewHolder(binding)
    }

    //this is the function that sets the values for each individual todo item
    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.binding.apply {
            tvTitle.text = todos[position].title
            cbDone.isChecked = todos[position].isChecked
        }
    }

    //this just returns how many todo_items there are in total. I was going to use with a textview to show how many todos are left undone, but I ran out of time.
    override fun getItemCount(): Int {
        return todos.size
    }
}