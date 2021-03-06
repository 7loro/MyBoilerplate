package com.casper.myboilerplate.todo.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.casper.myboilerplate.shared.delegate.viewBinding
import com.casper.myboilerplate.shared.presentation.extension.observe
import com.casper.myboilerplate.todo.R
import com.casper.myboilerplate.todo.databinding.FragmentTodoDetailBinding
import com.casper.myboilerplate.todo.domain.model.Todo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoDetailFragment : Fragment(R.layout.fragment_todo_detail) {

    private val binding: FragmentTodoDetailBinding by viewBinding()
    private val viewModel: TodoDetailViewModel by viewModels()

    private val stateObserver = Observer<TodoDetailViewModel.ViewState> {
        if (it.isDone) {
            val action =
                TodoDetailFragmentDirections.actionTodoDetailFragmentToTodoListFragment()
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        arguments?.let {
            val args = TodoDetailFragmentArgs.fromBundle(it)
            binding.title.setText(args.todoItem.title)
            binding.desc.setText(args.todoItem.desc)
            binding.deleteBtn.setOnClickListener {
                viewModel.delete(args.todoItem)
            }
            binding.updateBtn.setOnClickListener {
                viewModel.update(Todo(
                    id = args.todoItem.id,
                    title = binding.title.text.toString(),
                    desc = binding.desc.text.toString()
                ))
            }
        }
        observe(viewModel.stateLiveData, stateObserver)
    }
}