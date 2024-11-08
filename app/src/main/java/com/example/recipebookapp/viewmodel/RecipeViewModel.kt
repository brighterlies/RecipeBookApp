package com.example.recipebookapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.recipebookapp.data.model.RecipeModel
import com.example.recipebookapp.data.repository.RecipeRepositoryImp

class RecipeViewModel : ViewModel() {
    private val recipeRepositoryImp = RecipeRepositoryImp()

    suspend fun getAllRecipe() = recipeRepositoryImp.getAllRecipe()
    suspend fun getRecipeById(id: Int) = recipeRepositoryImp.getRecipeById(id)
    suspend fun insertRecipe(recipeModel: RecipeModel) = recipeRepositoryImp.insertRecipe(recipeModel)
    suspend fun updateRecipe(recipeModel: RecipeModel) = recipeRepositoryImp.updateRecipe(recipeModel)
    suspend fun deleteRecipe(recipeModel: RecipeModel) = recipeRepositoryImp.deleteRecipe(recipeModel)
    suspend fun deleteAllRecipes() = recipeRepositoryImp.deleteAllRecipes()

    suspend fun searchRecipe(charSequence: CharSequence) = recipeRepositoryImp.searchRecipe(charSequence)
}
