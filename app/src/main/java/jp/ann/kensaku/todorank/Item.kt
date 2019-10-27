package jp.ann.kensaku.todorank

import androidx.room.*

@Entity(tableName = "todo_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "done")
    var done: Boolean,

    @ColumnInfo(name = "rank")
    var rank: Int
)
