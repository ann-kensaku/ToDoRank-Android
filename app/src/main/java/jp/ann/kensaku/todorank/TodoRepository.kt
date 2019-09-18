package jp.ann.kensaku.todorank

import androidx.lifecycle.LiveData

class TodoRepository(private val todoDao: TodoDao) {

    val allTodos: LiveData<List<Item>> = todoDao.getAllTodos()

    suspend fun insert(todo: Item) {
        todoDao.insert(todo)
    }

    suspend fun update(todo: Item) {
        todoDao.update(todo)
    }
}