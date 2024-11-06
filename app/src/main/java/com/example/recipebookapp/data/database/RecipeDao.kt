package com.example.recipebookapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe_entity")
    suspend fun getAllRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM recipe_entity WHERE id = :id")
    suspend fun getRecipeById(id: Int): RecipeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeEntity: RecipeEntity)

    @Update
    suspend fun updateRecipe(recipeEntity: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipeEntity: RecipeEntity)

    @Query("DELETE FROM recipe_entity")
    suspend fun deleteAllRecipes()
}