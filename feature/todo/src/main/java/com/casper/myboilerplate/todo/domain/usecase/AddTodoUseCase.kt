package com.casper.myboilerplate.todo.domain.usecase

import com.casper.myboilerplate.todo.domain.model.Todo
import com.casper.myboilerplate.todo.domain.repository.TodoRepository
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    sealed interface Result {
        data class Success(val rowId: Long) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend fun execute(todo: Todo): Result {
        return runCatching {
            Result.Success(todoRepository.addTodoItem(Todo(
                title = todo.title,
                desc = todo.desc
            )))
        }.getOrElse {
            Result.Error(it)
        }
    }
}