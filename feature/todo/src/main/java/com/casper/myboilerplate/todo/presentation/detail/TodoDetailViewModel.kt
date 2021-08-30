package com.casper.myboilerplate.todo.presentation.detail

import androidx.lifecycle.viewModelScope
import com.casper.myboilerplate.shared.presentation.viewmodel.BaseAction
import com.casper.myboilerplate.shared.presentation.viewmodel.BaseViewModel
import com.casper.myboilerplate.shared.presentation.viewmodel.BaseViewState
import com.casper.myboilerplate.todo.domain.model.Todo
import com.casper.myboilerplate.todo.domain.usecase.DeleteTodoUseCase
import com.casper.myboilerplate.todo.domain.usecase.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
) : BaseViewModel<TodoDetailViewModel.ViewState, TodoDetailViewModel.Action>(ViewState()) {

    fun delete(todo: Todo) {
        viewModelScope.launch {
            deleteTodoUseCase.execute(todo).also { result ->
                val action = when (result) {
                    is DeleteTodoUseCase.Result.Success -> Action.TodoDeleteSuccess
                    is DeleteTodoUseCase.Result.Error -> Action.TodoDeleteFailure
                }
                sendAction(action)
            }
        }
    }

    fun update(todo: Todo) {
        viewModelScope.launch {
            updateTodoUseCase.execute(todo).also { result ->
                val action = when (result) {
                    is UpdateTodoUseCase.Result.Success -> Action.TodoUpdateSuccess
                    is UpdateTodoUseCase.Result.Error -> Action.TodoUpdateFailure
                }
                sendAction(action)
            }
        }
    }

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.TodoDeleteSuccess -> state.copy(
            isError = false,
            isDone = true
        )
        is Action.TodoDeleteFailure -> state.copy(
            isError = true,
            isDone = false
        )
        is Action.TodoUpdateSuccess -> state.copy(
            isError = false,
            isDone = true
        )
        is Action.TodoUpdateFailure -> state.copy(
            isError = true,
            isDone = false
        )
    }

    data class ViewState(
        val isError: Boolean = false,
        val isDone: Boolean = false
    ) : BaseViewState

    sealed interface Action : BaseAction {
        object TodoDeleteSuccess : Action
        object TodoDeleteFailure : Action
        object TodoUpdateSuccess : Action
        object TodoUpdateFailure : Action
    }
}
