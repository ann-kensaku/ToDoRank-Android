package jp.ann.kensaku.todorank

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TodoViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TodoRepository

    val allTodos: LiveData<List<Item>>

    init {
        val todoDao = TodoDatabase.getDatabase(application, viewModelScope).todoDao()
        repository = TodoRepository(todoDao)
        allTodos = repository.allTodos
    }

    fun insert(todo: Item) = viewModelScope.launch {
        repository.insert(todo)
    }

    fun update(todo: Item) = viewModelScope.launch{
        repository.update(todo)
    }

}