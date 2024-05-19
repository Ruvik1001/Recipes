package com.tasks.domain.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class], version = 1)
abstract class DB : RoomDatabase() {
    abstract fun recipeDao(): DAO
}
