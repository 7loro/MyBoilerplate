package com.casper.myboilerplate.todo.data.di

import com.casper.myboilerplate.todo.data.repository.TodoRepositoryImpl
import com.casper.myboilerplate.todo.domain.repository.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TodoDataModule {
    @Binds
    abstract fun bindsTodoRepository(
        repository: TodoRepositoryImpl
    ): TodoRepository
}
