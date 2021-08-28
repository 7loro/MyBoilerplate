package com.casper.myboilerplate.todo.presentation.detail

import androidx.lifecycle.viewModelScope
import com.casper.myboilerplate.shared.presentation.viewmodel.BaseAction
import com.casper.myboilerplate.shared.presentation.viewmodel.BaseViewModel
import com.casper.myboilerplate.shared.presentation.viewmodel.BaseViewState
import com.casper.myboilerplate.todo.domain.model.Todo
import com.casper.myboilerplate.todo.domain.usecase.AddTodoUseCase
import com.casper.myboilerplate.todo.domain.usecase.DeleteTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val deleteTodoUseCase: DeleteTodoUseCase
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

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.TodoDeleteSuccess -> state.copy(
            isError = false,
            isDeleted = true
        )
        is Action.TodoDeleteFailure -> state.copy(
            isError = true,
            isDeleted = false
        )
    }

    data class ViewState(
        val isError: Boolean = false,
        val isDeleted: Boolean = false
    ) : BaseViewState

    sealed interface Action : BaseAction {
        object TodoDeleteSuccess : Action
        object TodoDeleteFailure : Action
    }
}
