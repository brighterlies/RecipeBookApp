package com.example.recipebookapp.data.model

data class RecipeResponse(
    val data: List<RecipeModel>
)

data class RecipeModel(
    val id: Long,
    val title: String,
    //val course: String
    val cuisine: String,
    //val mainIngredient: String,
    val description: String,
    //val source: String,
    //val url: String,
    //val urlHost: String,
    //val prepTime: Long,
    //val cookTime: Long,
    val totalTime: Long,
    //val servings: Long,
    //val yield: Any?,
    val ingredients: String,
    val directions: String,
    val tags: String,
    val rating: String,
    //val publicUrl: String,
    val photoUrl: String,
    //val private: String,
    //val nutritionalScoreGeneric: String,
    //val calories: Any?,
    //val fat: String,
    //val cholesterol: String,
    //val sodium: String,
    //val sugar: String,
    //val carbohydrate: String,
    //val fiber: String,
    //val protein: String,
    //val cost: String,
)