package com.casper.myboilerplate.todo.domain.usecase

import com.casper.myboilerplate.todo.domain.model.Todo
import com.casper.myboilerplate.todo.domain.repository.TodoRepository
import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    sealed interface Result {
        data class Success(val rowId: Int) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend fun execute(todo: Todo): Result {
        return runCatching {
            Result.Success(todoRepository.updateTodoItem(todo))
        }.getOrElse {
            Result.Error(it)
        }
    }
}
