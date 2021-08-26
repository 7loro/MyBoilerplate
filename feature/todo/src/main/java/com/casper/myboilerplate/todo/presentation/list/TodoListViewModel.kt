package com.casper.myboilerplate.todo.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.casper.myboilerplate.todo.domain.model.Todo
import kotlinx.coroutines.launch

class TodoListViewModel : ViewModel() {
    private val _todoItems = MutableLiveData<List<Todo>>()
    val todoItems: LiveData<List<Todo>> = _todoItems

    init {
        viewModelScope.launch {
            // TODO usecase 통해서 db, remote 데이터 읽어오기
            val mockUpData = listOf(
                Todo("Todo1", "Todo1 desc"),
                Todo("Todo2", "Todo2 desc"),
                Todo("Todo3", "Todo3 desc"),
                Todo("Todo4", "Todo4 desc")
            )
            _todoItems.value = mockUpData
        }
    }
}