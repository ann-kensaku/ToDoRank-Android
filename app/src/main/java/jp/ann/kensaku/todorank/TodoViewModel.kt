package jp.ann.kensaku.todorank

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
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

    fun update(todo: Item) = viewModelScope.launch {
        repository.update(todo)
    }

    fun delete(todo: Item) = viewModelScope.launch {
        val all = allTodos.value ?: return@launch

        all.takeLastWhile { it != todo }
            .map { it.copy(rank = it.rank - 1) }
            .forEach { repository.update(it) }
        repository.delete(todo)
    }

    fun add(activity: MainActivity, todo: Item, high: Int, low: Int) =
        viewModelScope.launch {
            val all = allTodos.value ?: return@launch
            val targettext = todo.title
            var middle = (low + high) / 2

            //初めての追加ならば、そのままリストに加える
            if (all.size == 0) {
                repository.insert(todo)
            } else {
                if (low - high > 1) {
                    middle = (low + high) / 2
                    val comparetext = all.get(middle - 1).title ?: ""
                    RankActivity.launch(activity, RESULT_RANK_ACTIVITY, targettext, comparetext)
                } else {
                    all.takeLastWhile { it.rank != middle }
                        .map { it.copy(rank = it.rank + 1) }
                        .forEach { repository.update(it) }
                    repository.insert(todo)
                }
            }
        }

    companion object {
        private const val RESULT_RANK_ACTIVITY = 1000
    }
}