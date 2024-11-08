package com.example.recipebookapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipebookapp.RecipeBookApp
import com.example.recipebookapp.data.mapper.recipeEntityToRecipe
import com.example.recipebookapp.data.mapper.recipeEntityToRecipeList
import com.example.recipebookapp.data.mapper.toRecipeEntity
import com.example.recipebookapp.data.model.RecipeModel
import com.example.recipebookapp.data.network.RecipeApiClient
import com.example.recipebookapp.data.provider.RecipeProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepositoryImp : RecipeRepository {

    private val recipeProvider : RecipeProvider = RecipeProvider()

    private val _recipes = MutableLiveData<List<RecipeModel>>()
    private val recipes: LiveData<List<RecipeModel>> get() = _recipes

    override suspend fun getAllRecipe(): LiveData<List<RecipeModel>> {
        withContext(Dispatchers.IO) {
            var recipeFromDatabase = RecipeBookApp.database.recipeDao().getAllRecipes()
            if (recipeFromDatabase.isEmpty()) {
                val recipeFromApi = getRecipeFromApi()
                recipeFromApi?.forEach {
                    RecipeBookApp.database.recipeDao().insertRecipe(it.toRecipeEntity())
                }
                recipeFromDatabase = RecipeBookApp.database.recipeDao().getAllRecipes()
            }
            _recipes.postValue(recipeEntityToRecipeList(recipeFromDatabase))
        }
        return recipes
    }

    override suspend fun getRecipeById(id: Int): LiveData<RecipeModel?> {
        val recipeLiveData = MutableLiveData<RecipeModel?>()
        withContext(Dispatchers.IO) {
            val recipeFromDatabase = RecipeBookApp.database.recipeDao().getRecipeById(id)
            recipeLiveData.postValue(recipeFromDatabase?.let { recipeEntityToRecipe(it) })
        }
        return recipeLiveData
    }

    override suspend fun insertRecipe(recipeModel: RecipeModel) {
        withContext(Dispatchers.IO) {
            RecipeBookApp.database.recipeDao().insertRecipe(recipeModel.toRecipeEntity())
            refreshRecipes()
        }
    }

    override suspend fun updateRecipe(recipeModel: RecipeModel) {
        withContext(Dispatchers.IO) {
            RecipeBookApp.database.recipeDao().updateRecipe(recipeModel.toRecipeEntity())
            refreshRecipes()
        }
    }

    override suspend fun deleteRecipe(recipeModel: RecipeModel) {
        withContext(Dispatchers.IO) {
            RecipeBookApp.database.recipeDao().deleteRecipe(recipeModel.toRecipeEntity())
            refreshRecipes()
        }
    }

    override suspend fun deleteAllRecipes() {
        withContext(Dispatchers.IO) {
            RecipeBookApp.database.recipeDao().deleteAllRecipes()
            refreshRecipes()
        }
    }

    private suspend fun getRecipeFromApi(): List<RecipeModel>? {
        return withContext(Dispatchers.IO) {
            val retrofit = recipeProvider.providerRetrofit()
            val apiClient = retrofit.create(RecipeApiClient::class.java)
            val response = apiClient.getAllRecipe()

            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                println("Error in Response: ${response.code()}")
                null
            }
        }
    }

    suspend fun searchRecipe(charSequence: CharSequence): LiveData<List<RecipeModel>> {
        withContext(Dispatchers.IO) {
            var recipeFromDatabase = RecipeBookApp.database.recipeDao().getAllRecipes()
            val recipesFiltered = recipeFromDatabase.filter { recipe ->
                recipe.title.contains(charSequence, ignoreCase = true)
                        || recipe.description.contains(charSequence, ignoreCase = true)
            }
            _recipes.postValue(recipeEntityToRecipeList(recipesFiltered))
        }
        return recipes
    }

    private suspend fun refreshRecipes() {
        val recipeFromDatabase = RecipeBookApp.database.recipeDao().getAllRecipes()
        _recipes.postValue(recipeEntityToRecipeList(recipeFromDatabase))
    }
}