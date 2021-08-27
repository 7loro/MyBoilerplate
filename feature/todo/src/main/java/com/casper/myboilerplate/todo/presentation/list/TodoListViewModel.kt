package com.casper.myboilerplate.todo.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casper.myboilerplate.shared.presentation.viewmodel.BaseAction
import com.casper.myboilerplate.shared.presentation.viewmodel.BaseViewModel
import com.casper.myboilerplate.shared.presentation.viewmodel.BaseViewState
import com.casper.myboilerplate.todo.domain.model.Todo
import com.casper.myboilerplate.todo.domain.usecase.GetTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val getTodoUseCase: GetTodoUseCase
) : BaseViewModel<TodoListViewModel.ViewState, TodoListViewModel.Action>(ViewState()) {

    private val _todoItems = MutableLiveData<List<Todo>>()
    val todoItems: LiveData<List<Todo>> = _todoItems

    override fun onLoadData() {
        getTodoList()
    }

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.TodoListLoadingSuccess -> state.copy(
            isLoading = false,
            isError = false,
            todolist = viewAction.todolist
        )
        is Action.TodoListLoadingFailure -> state.copy(
            isLoading = false,
            isError = true,
            todolist = listOf()
        )
    }

    private fun getTodoList() {
        viewModelScope.launch {
            getTodoUseCase.execute().also { result ->
                val action = when (result) {
                    is GetTodoUseCase.Result.Success ->
                        if (result.data.isEmpty()) {
                            Action.TodoListLoadingFailure
                        } else {
                            Action.TodoListLoadingSuccess(result.data)
                        }

                    is GetTodoUseCase.Result.Error ->
                        Action.TodoListLoadingFailure
                }
                sendAction(action)
            }
        }
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val todolist: List<Todo> = listOf()
    ) : BaseViewState

    sealed interface Action : BaseAction {
        class TodoListLoadingSuccess(val todolist: List<Todo>) : Action
        object TodoListLoadingFailure : Action
    }
}
