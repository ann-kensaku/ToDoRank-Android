package jp.ann.kensaku.todorank

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_table")
    fun getAllTodos(): LiveData<List<Item>>

    @Query("SELECT * FROM todo_table WHERE id = :id")
    fun byId(id: Long): LiveData<Item?>

    @Query("SELECT COUNT(*) FROM todo_table")
    fun count(): Int

    @Query("DELETE FROM todo_table WHERE done == :done")
    suspend fun deleteDone(done: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Item)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteItem(todo: Item)

    @Update
    suspend fun update(tododata: Item)

}