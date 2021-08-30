package com.casper.myboilerplate.todo.data.database

import androidx.room.*
import com.casper.myboilerplate.todo.data.database.model.TodoEntity

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo ORDER BY id DESC")
    suspend fun getAll(): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: TodoEntity): Long

    @Update
    suspend fun update(todo: TodoEntity): Int

    @Query("DELETE FROM todo WHERE id=:id")
    suspend fun delete(id: Int): Int

    @Query("DELETE FROM todo")
    suspend fun deleteAll()
}
