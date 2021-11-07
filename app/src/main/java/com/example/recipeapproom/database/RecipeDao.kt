package com.example.recipeapproom.database

import androidx.room.*

@Dao
interface RecipeDao {

    //add a new Note
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecipe(item: RecipeEntity)

    // a query to bring all the notes in the database
    @Query("SELECT * FROM RecipeTable ORDER BY id ASC")
    fun getRecipes(): List<RecipeEntity>

    //update an existing note
    @Update
    suspend fun updateRecipe(item: RecipeEntity)

    //delete note
    @Delete
    suspend fun deleteRecipe(item: RecipeEntity)

}