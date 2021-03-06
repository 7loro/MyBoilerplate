package com.casper.myboilerplate.todo.presentation.add

import androidx.lifecycle.viewModelScope
import com.casper.myboilerplate.shared.presentation.viewmodel.BaseAction
import com.casper.myboilerplate.shared.presentation.viewmodel.BaseViewModel
import com.casper.myboilerplate.shared.presentation.viewmodel.BaseViewState
import com.casper.myboilerplate.todo.domain.model.Todo
import com.casper.myboilerplate.todo.domain.usecase.AddTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoAddViewModel @Inject constructor(
    private val addTodoUseCase: AddTodoUseCase
) : BaseViewModel<TodoAddViewModel.ViewState, TodoAddViewModel.Action>(ViewState()) {

    fun addTodo(todo: Todo) {
        viewModelScope.launch {
            addTodoUseCase.execute(todo).also { result ->
                val action = when (result) {
                    is AddTodoUseCase.Result.Success -> Action.TodoAddSuccess
                    is AddTodoUseCase.Result.Error -> Action.TodoAddFailure
                }
                sendAction(action)
            }
        }
    }

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.TodoAddSuccess -> state.copy(
            isLoading = false,
            isError = false,
            isSaved = true
        )
        is Action.TodoAddFailure -> state.copy(
            isLoading = false,
            isError = true,
            isSaved = false
        )
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val isSaved: Boolean = false
    ) : BaseViewState

    sealed interface Action : BaseAction {
        object TodoAddSuccess : Action
        object TodoAddFailure : Action
    }
}
