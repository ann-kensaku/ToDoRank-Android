package jp.ann.kensaku.todorank

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoData (
    @PrimaryKey
    val id: Int,
    val todoItem: String,
    val done: Boolean
)