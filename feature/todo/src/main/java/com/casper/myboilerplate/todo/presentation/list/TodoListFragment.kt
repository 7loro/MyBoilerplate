package com.casper.myboilerplate.todo.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.casper.myboilerplate.shared.delegate.viewBinding
import com.casper.myboilerplate.todo.R
import com.casper.myboilerplate.todo.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment(R.layout.fragment_todo_list) {
    private val binding: FragmentTodoListBinding by viewBinding()
    private val viewModel: TodoListViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.todoItems.observe(viewLifecycleOwner) {
            // TODO livedata 가 바뀔 때 마다 모두 새로 셋업하는 형태인데, Diffutil 사용 검토할 것
            binding.todoList.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(
                    VerticalSpaceItemDecoration(resources.getDimensionPixelSize(
                    R.dimen.todo_list_vertical_space
                ))
                )
                val todoAdapter = TodoAdapter(it).apply {
                    setOnClickListener {
                        // TODO navigation util 만들기
                        val action =
                            TodoListFragmentDirections.actionTodoListFragmentToTodoDetailFragment(
                                it.title,
                                it.desc
                            )
                        findNavController().navigate(action)
                    }
                }
                adapter = todoAdapter
            }
            binding.addBtn.setOnClickListener {
                val action =
                    TodoListFragmentDirections.actionTodoListFragmentToTodoAddFragment()
                findNavController().navigate(action)
            }
        }
    }
}