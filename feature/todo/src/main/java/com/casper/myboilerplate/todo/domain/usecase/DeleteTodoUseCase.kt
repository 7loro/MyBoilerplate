package com.casper.myboilerplate.todo.domain.usecase

import com.casper.myboilerplate.todo.domain.model.Todo
import com.casper.myboilerplate.todo.domain.repository.TodoRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {

    sealed interface Result {
        data class Success(val deletedRowNum: Int) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend fun execute(todo: Todo): Result {
        return runCatching {
            todo.id?.let {
                val deletedRowNum = todoRepository.deleteTodoItem(it)
                if (deletedRowNum > 0) Result.Success(deletedRowNum)
                else Result.Error(IllegalArgumentException("No item deleted"))
            } ?: run {
                Result.Error(IllegalArgumentException("No item id exist"))
            }
        }.getOrElse {
            Result.Error(it)
        }
    }
}