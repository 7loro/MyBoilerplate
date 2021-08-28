package com.casper.myboilerplate.todo.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.casper.myboilerplate.todo.data.database.model.TodoEntity

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo ORDER BY id DESC")
    suspend fun getAll(): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: TodoEntity): Long

    @Query("DELETE FROM todo WHERE id=:id")
    suspend fun delete(id: Int): Int

    @Query("DELETE FROM todo")
    suspend fun deleteAll()
}
