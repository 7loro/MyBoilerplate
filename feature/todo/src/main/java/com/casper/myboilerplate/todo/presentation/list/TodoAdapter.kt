package com.casper.myboilerplate.todo.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.casper.myboilerplate.shared.delegate.observer
import com.casper.myboilerplate.todo.databinding.TodoItemBinding
import com.casper.myboilerplate.todo.domain.model.Todo
import javax.inject.Inject

class TodoAdapter @Inject constructor() : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    var todoItems: List<Todo> by observer(listOf()) {
        notifyDataSetChanged()
    }

    private var onClickListener: ((todo: Todo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TodoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(todoItems[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = todoItems.size

    fun setOnClickListener(listener: (album: Todo) -> Unit) {
        this.onClickListener = listener
    }

    inner class ViewHolder(private val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.title.text = todo.title
            binding.desc.text = todo.desc
            itemView.setOnClickListener { onClickListener?.invoke(todo) }
        }
    }
}