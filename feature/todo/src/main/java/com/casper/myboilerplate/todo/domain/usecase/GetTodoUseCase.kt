package com.casper.myboilerplate.todo.domain.usecase

import com.casper.myboilerplate.todo.domain.model.Todo
import com.casper.myboilerplate.todo.domain.repository.TodoRepository
import javax.inject.Inject

class GetTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    sealed interface Result {
        data class Success(val data: List<Todo>) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend fun execute(): Result {
        return runCatching {
            Result.Success(todoRepository.getTodoList())
        }.getOrElse {
            Result.Error(it)
        }
    }
}