package com.example.recipebookapp.data.repository

import androidx.lifecycle.LiveData
import com.example.recipebookapp.data.model.RecipeModel

interface RecipeRepository {
    suspend fun getAllRecipe(): LiveData<List<RecipeModel>>
    suspend fun getRecipeById(id: Int): LiveData<RecipeModel?>
    suspend fun insertRecipe(recipeModel: RecipeModel)
    suspend fun updateRecipe(recipeModel: RecipeModel)
    suspend fun deleteRecipe(recipeModel: RecipeModel)
    suspend fun deleteAllRecipes()
}