package com.example.recipebookapp.data.mapper

import com.example.recipebookapp.data.database.RecipeEntity
import com.example.recipebookapp.data.model.RecipeModel

fun RecipeModel.toRecipeEntity(): RecipeEntity = RecipeEntity(
    id = id.toInt(),
    title = title,
    cuisine = cuisine,
    description = description,
    tags = tags,
    photoUrl = photoUrl,
    ingredients = ingredients,
    directions = directions,
    totalTime = totalTime,
    rating = rating
)

fun recipeEntityToRecipe(recipeEntity: RecipeEntity): RecipeModel = RecipeModel(
    id = recipeEntity.id.toLong(),
    title = recipeEntity.title,
    cuisine = recipeEntity.cuisine,
    description = recipeEntity.description,
    tags = recipeEntity.tags,
    photoUrl = recipeEntity.photoUrl,
    ingredients = recipeEntity.ingredients,
    directions = recipeEntity.directions,
    totalTime = recipeEntity.totalTime,
    rating = recipeEntity.rating
)

fun recipeEntityToRecipeList(list: List<RecipeEntity>): List<RecipeModel> = list.map(::recipeEntityToRecipe)