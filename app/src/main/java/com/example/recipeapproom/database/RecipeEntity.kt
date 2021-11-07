package com.example.recipeapproom.database

import androidx.room.Entity
import androidx.room.PrimaryKey

//table name
@Entity(tableName = "RecipeTable")
data class RecipeEntity(

    // table contents
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val author: String,
    val ingredients: String,
    val instruction: String)