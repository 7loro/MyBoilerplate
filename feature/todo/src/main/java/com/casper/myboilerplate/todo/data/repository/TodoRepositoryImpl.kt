package com.casper.myboilerplate.todo.data.repository

import com.casper.myboilerplate.todo.domain.model.Todo
import com.casper.myboilerplate.todo.domain.repository.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor() : TodoRepository {
    override suspend fun getTodoList(): List<Todo> {
        val mockup = mutableListOf<Todo>()
        (1..1000).iterator().forEach {
            mockup.add(Todo("Title$it", "Desc$it"))
        }
        return mockup
    }
}