package com.casper.myboilerplate.todo.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.casper.myboilerplate.shared.delegate.viewBinding
import com.casper.myboilerplate.shared.presentation.extension.observe
import com.casper.myboilerplate.shared.presentation.extension.visible
import com.casper.myboilerplate.todo.R
import com.casper.myboilerplate.todo.databinding.FragmentTodoListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TodoListFragment : Fragment(R.layout.fragment_todo_list) {

    private val binding: FragmentTodoListBinding by viewBinding()
    private val viewModel: TodoListViewModel by activityViewModels()
    @Inject lateinit var todoAdapter: TodoAdapter
    private val stateObserver = Observer<TodoListViewModel.ViewState> {
        todoAdapter.todoItems = it.todolist

        binding.progressBar.visible = it.isLoading
        binding.errorAnimation.visible = it.isError
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoAdapter.setOnClickListener {
            // TODO navigation util 만들기
            val action =
                TodoListFragmentDirections.actionTodoListFragmentToTodoDetailFragment(
                    it.title,
                    it.desc
                )
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                VerticalSpaceItemDecoration(resources.getDimensionPixelSize(
                    R.dimen.todo_list_vertical_space
                ))
            )
            adapter = todoAdapter
        }

        binding.addBtn.setOnClickListener {
            val action =
                TodoListFragmentDirections.actionTodoListFragmentToTodoAddFragment()
            findNavController().navigate(action)
        }

        observe(viewModel.stateLiveData, stateObserver)

        viewModel.loadData()
    }
}