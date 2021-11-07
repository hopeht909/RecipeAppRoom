package com.example.recipeapproom

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapproom.database.RecipeDatabase
import com.example.recipeapproom.database.RecipeEntity
import com.example.recipeapproom.database.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val recipesDao by lazy { RecipeDatabase.getDatabase(this).recipeDao()}
    private val repository  by lazy { RecipeRepository(recipesDao) }

    lateinit var btSave : Button

    lateinit var etTitle : EditText
    lateinit var etAuthor : EditText
    lateinit var etIngredients : EditText
    lateinit var etInstruction : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btSave = findViewById(R.id.btSave)

        etTitle = findViewById(R.id.etTitle)
        etAuthor = findViewById(R.id.etAuthor)
        etIngredients = findViewById(R.id.etIngredients)
        etInstruction = findViewById(R.id.etInstruction)



        val title = etTitle.text.toString()
        val author = etAuthor.text.toString()
        val ingredients = etIngredients.text.toString()
        val instruction = etInstruction.text.toString()


        btSave.setOnClickListener {
            if(etTitle.text.isNotBlank() &&etAuthor.text.isNotBlank()
                && etIngredients.text.isNotBlank()&& etInstruction.text.isNotBlank()){

                val title = etTitle.text.toString()
                val author = etAuthor.text.toString()
                val ingredients = etIngredients.text.toString()
                val instruction = etInstruction.text.toString()

                addRecipe(title, author, ingredients, instruction)
                etTitle.text.clear()
                etTitle.clearFocus()

                etAuthor.text.clear()
                etAuthor.clearFocus()

                etIngredients.text.clear()
                etIngredients.clearFocus()

                etInstruction.text.clear()
                etInstruction.clearFocus()


                Toast.makeText(this, "Recipe Added", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "please add a Recipe", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun viewReceipe(view: android.view.View) {
     val intent = Intent(this, ViewRecipes::class.java)
        startActivity(intent)
    }
    fun addRecipe(title: String, author: String, ingredients: String, instruction: String){
        CoroutineScope(IO).launch {
            repository.addRecipe(RecipeEntity(0, title,author,ingredients, instruction))
        }
    }

}