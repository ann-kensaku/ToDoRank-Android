package jp.ann.kensaku.todorank

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Item::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao

    private class TodoDatabaseCallback(
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let {
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(
            context: Context
        ): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_table"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(TodoDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}