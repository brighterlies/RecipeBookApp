package com.example.recipebookapp.data.network

import com.example.recipebookapp.data.model.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface RecipeApiClient {
    @GET(".")
    suspend fun getAllRecipe(): Response<RecipeResponse>
}