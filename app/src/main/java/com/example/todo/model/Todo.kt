package com.example.todo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")

data class Todo(
    var title: String,
    var date: String,
) {

    constructor(date: String) : this("", date)

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var description: String = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0
}