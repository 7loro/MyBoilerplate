package com.casper.myboilerplate.todo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.casper.myboilerplate.todo.data.database.model.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
}
