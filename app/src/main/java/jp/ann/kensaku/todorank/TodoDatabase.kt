package jp.ann.kensaku.todorank

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Item::class], version = 2)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao

    private class TodoDatabaseCallback(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let {todoDatabase ->
                scope.launch {
                    val todoDao = todoDatabase.todoDao()

                    //Delete all content here
                    todoDao.deleteAll()

                    //Add sample
                    val todo = Item(title="やること1")
                    todoDao.insert(todo)

                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_table"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(TodoDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}