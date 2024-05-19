package com.tasks.domain.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class Recipe(
    var name: String,
    var description: String,
    var ingredients: String,
    var timestamp: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
