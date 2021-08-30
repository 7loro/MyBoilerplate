package com.casper.myboilerplate.todo.data.repository

import com.casper.myboilerplate.todo.data.database.TodoDao
import com.casper.myboilerplate.todo.data.database.model.toDomainModel
import com.casper.myboilerplate.todo.data.database.model.toEntity
import com.casper.myboilerplate.todo.domain.model.Todo
import com.casper.myboilerplate.todo.domain.repository.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val dao: TodoDao
) : TodoRepository {
    override suspend fun getTodoList(): List<Todo> {
        return dao.getAll().map { it.toDomainModel() }
    }

    override suspend fun addTodoItem(item: Todo): Long {
        return dao.insert(item.toEntity())
    }

    override suspend fun updateTodoItem(item: Todo): Int {
        return dao.update(item.toEntity())
    }

    override suspend fun deleteTodoItem(id: Int): Int {
        return dao.delete(id)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }
}
