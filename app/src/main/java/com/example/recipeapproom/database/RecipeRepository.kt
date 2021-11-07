package com.example.recipeapproom.database

//class that make it easier to reach the methods we created in Doa CLass
class RecipeRepository(private val recipeDao: RecipeDao) {

    val getNotes: List<RecipeEntity> = recipeDao.getRecipes()

    // suspend fun so we can reach it in the Main easily and resume it whenever
    suspend fun addRecipe(item: RecipeEntity){
        recipeDao.addRecipe(item)
    }

    suspend fun updateRecipe(item: RecipeEntity){
        recipeDao.updateRecipe(item)
    }

    suspend fun deleteRecipe(item: RecipeEntity){
        recipeDao.deleteRecipe(item)
    }

}