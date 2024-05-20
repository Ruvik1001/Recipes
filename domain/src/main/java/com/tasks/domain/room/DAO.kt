package com.tasks.domain.room
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DAO {
    @Query("SELECT * FROM recipe_table")
    suspend fun getAll(): List<Recipe>

    @Query("SELECT * FROM recipe_table ORDER BY timestamp DESC")
    suspend fun getAllSortedByTimestamp(): List<Recipe>

    @Query("SELECT * FROM recipe_table WHERE id = :recipeId")
    suspend fun getById(recipeId: Long): Recipe?

    @Insert
    suspend fun insert(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Query("DELETE FROM recipe_table WHERE id = :recipeId")
    suspend fun delete(recipeId: Long)
}
