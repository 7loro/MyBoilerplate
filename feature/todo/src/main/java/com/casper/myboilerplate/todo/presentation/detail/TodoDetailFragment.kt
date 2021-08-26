package com.casper.myboilerplate.todo.presentation.detail

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.casper.myboilerplate.todo.R

class TodoDetailFragment : Fragment(R.layout.fragment_todo_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val args = TodoDetailFragmentArgs.fromBundle(it)
            view.findViewById<TextView>(R.id.title).text = args.title
            view.findViewById<TextView>(R.id.desc).text = args.desc
        }
    }
}