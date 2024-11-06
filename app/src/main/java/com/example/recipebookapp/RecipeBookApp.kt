package com.example.recipebookapp

import android.app.Application
import com.example.recipebookapp.data.database.RecipeDatabase
import com.example.recipebookapp.data.provider.RecipeProvider

class RecipeBookApp : Application() {
    private val recipeProvider: RecipeProvider = RecipeProvider()

    companion object {
        lateinit var database: RecipeDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = recipeProvider.providerRoom(this)
    }
}