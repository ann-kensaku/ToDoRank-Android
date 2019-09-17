package jp.ann.kensaku.todorank

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.security.AccessControlContext
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class TodoRepository(private val todoDao: TodoDao) {

    val allTodos: LiveData<List<Item>> = todoDao.getAllTodos()

    suspend fun insert(todo: Item) {
        todoDao.insert(todo)
    }
}