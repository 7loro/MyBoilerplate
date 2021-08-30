package com.casper.myboilerplate.todo.domain.repository

import com.casper.myboilerplate.todo.domain.model.Todo

interface TodoRepository {

    suspend fun getTodoList(): List<Todo>
    suspend fun addTodoItem(item: Todo): Long
    suspend fun updateTodoItem(item: Todo): Int
    suspend fun deleteTodoItem(id: Int): Int
    suspend fun deleteAll()
}
