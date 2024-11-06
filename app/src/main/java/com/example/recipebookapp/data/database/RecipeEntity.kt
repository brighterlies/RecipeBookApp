package com.example.recipebookapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_entity")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "tags")
    var tags: String,
    @ColumnInfo(name = "photoUrl")
    var photoUrl: String,
    @ColumnInfo(name = "ingredients")
    var ingredients: String,
    @ColumnInfo(name = "directions")
    var directions: String,
    @ColumnInfo(name = "totalTime")
    var totalTime: Long,
    @ColumnInfo(name = "cuisine")
    var cuisine: String,
    @ColumnInfo(name = "rating")
    var rating: String,
)