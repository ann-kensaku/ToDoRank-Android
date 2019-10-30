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
        val todoDao = TodoDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(todoDao)
        allTodos = repository.allTodos
    }

    fun insert(todo: Item) = viewModelScope.launch {
        repository.insert(todo)
    }

    fun update(todo: Item) = viewModelScope.launch{
        repository.update(todo)
    }

    fun delete(todo: Item) = viewModelScope.launch {
        val all = allTodos.value ?: return@launch

        all.takeLastWhile { it != todo }
            .map { it.copy(rank = it.rank - 1) }
            .forEach { repository.update(it) }
        repository.delete(todo)
    }

    fun deleteChecked() = viewModelScope.launch {
        val all = allTodos.value ?: return@launch
        var rank = 1

        for (todo in all) {
            if (todo.done == true) {
                repository.delete(todo)
            }
            else {
                todo.rank = rank++
                repository.update(todo)
            }
        }
    }

}