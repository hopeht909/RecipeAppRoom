package com.example.recipeapproom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//The class name must match the one we created before
@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
abstract class RecipeDatabase: RoomDatabase() {

    //an object from NoteDoa Class so we can all it in the MainActivity
    abstract fun recipeDao(): RecipeDao

    // a constant code to activate the database
    companion object{
        @Volatile  // writes to this field are immediately visible to other threads
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){  // protection from concurrent execution on multiple threads
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "note_database" // databaseName
                ).fallbackToDestructiveMigration()  // Destroys old database on version change
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}