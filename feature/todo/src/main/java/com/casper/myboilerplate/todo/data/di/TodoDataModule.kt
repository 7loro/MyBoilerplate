package com.casper.myboilerplate.todo.data.di

import android.content.Context
import androidx.room.Room
import com.casper.myboilerplate.todo.data.database.TodoDao
import com.casper.myboilerplate.todo.data.database.TodoDatabase
import com.casper.myboilerplate.todo.data.repository.TodoRepositoryImpl
import com.casper.myboilerplate.todo.domain.repository.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TodoDataModule {
    /**
     * Binds 는 구현체를 우리 앱 내에 가지고 있는 경우
     * Provide 는 interface 만 가지고 있는 경우
     */
    @Binds
    abstract fun bindsTodoRepository(
        repository: TodoRepositoryImpl
    ): TodoRepository


    @InstallIn(SingletonComponent::class)
    @Module
    internal object DatabaseModule {
        @Provides
        @Singleton
        fun provideTodoDao(todoDatabase: TodoDatabase): TodoDao {
            return todoDatabase.todoDao()
        }

        @Provides
        @Singleton
        fun provideTodoDatabase(@ApplicationContext appContext: Context): TodoDatabase {
            return Room.databaseBuilder(
                appContext,
                TodoDatabase::class.java,
                "todo"
            ).build()
        }
    }
}
