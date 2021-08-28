package com.casper.myboilerplate.todo.presentation.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.casper.myboilerplate.shared.delegate.viewBinding
import com.casper.myboilerplate.shared.presentation.extension.observe
import com.casper.myboilerplate.shared.presentation.extension.visible
import com.casper.myboilerplate.todo.R
import com.casper.myboilerplate.todo.databinding.FragmentTodoAddBinding
import com.casper.myboilerplate.todo.domain.model.Todo
import com.casper.myboilerplate.todo.presentation.list.TodoListFragmentDirections
import com.casper.myboilerplate.todo.presentation.list.TodoListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoAddFragment : Fragment(R.layout.fragment_todo_add) {

    private val binding: FragmentTodoAddBinding by viewBinding()
    private val viewModel: TodoAddViewModel by viewModels()
    private val stateObserver = Observer<TodoAddViewModel.ViewState> {
        if (it.isSaved) {
            val action =
                TodoAddFragmentDirections.actionTodoAddFragmentToTodoListFragment()
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        binding.saveBtn.setOnClickListener {
            // TODO Add validate
            viewModel.addTodo(Todo(
                title = binding.title.text.toString(),
                desc = binding.desc.text.toString())
            )
        }
        observe(viewModel.stateLiveData, stateObserver)
    }
}