package com.example.recipeapproom

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapproom.database.RecipeDatabase
import com.example.recipeapproom.database.RecipeEntity
import com.example.recipeapproom.database.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ViewRecipes : AppCompatActivity() {
    private val recipesDao by lazy { RecipeDatabase.getDatabase(this).recipeDao()}
    private val repository  by lazy { RecipeRepository(recipesDao) }
    lateinit var rvView : RecyclerView
    lateinit var lv : List<RecipeEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_recipes)
        rvView = findViewById(R.id.rvView)
        lv = listOf()
        getNotesList()
        updateRV()
    }
    fun getNotesList(){
        CoroutineScope(Dispatchers.IO).launch {
            val data = async {
                repository.getNotes
            }.await()
            if(data.isNotEmpty()){
                lv = data
                updateRV()
            }else{
                Log.e("MainActivity", "Unable to get data", )
            }
        }
    }
    fun updateRV(){
        rvView.adapter = RVAdapter(this, lv)
        rvView.layoutManager = LinearLayoutManager(this)

    }
    fun updateRecipe(ID : Int, title: String, author: String, ingredients: String, instruction: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateRecipe(RecipeEntity(ID, title,author,ingredients,instruction))
        }
    }

    fun deleteNote(ID : Int, title: String, author: String, ingredients: String, instruction: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteRecipe(RecipeEntity(ID, title,author,ingredients,instruction))
        }
    }


    fun raiseDialog(id: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val mLayout = LinearLayout(this)

        val title = EditText(this)
        title.hint = "Enter the new title"

        val author = EditText(this)
        author.hint = "Enter the new author"

        val ingredients = EditText(this)
        ingredients.hint = "Enter the new ingredients"

        val instruction = EditText(this)
        instruction.hint = "Enter the new instruction"


        mLayout.orientation = LinearLayout.VERTICAL
        mLayout.addView(title)
        mLayout.addView(author)
        mLayout.addView(ingredients)
        mLayout.addView(instruction)

        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {

                    _, _ ->
                run{

                    val title = title.text.toString()
                    val author = author.text.toString()
                    val ingredients = ingredients.text.toString()
                    val instruction = instruction.text.toString()

                    updateRecipe(id,title,author,ingredients,instruction )
                    Toast.makeText(this, "Note Updated", Toast.LENGTH_LONG).show()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(mLayout)
        alert.show()
    }
    fun checkDeleteDialog(id: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val checkTextView = TextView(this)
        checkTextView.text = "  Are sure you want to delete this note ?!"

        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {

                    _, _ ->
                run{
                    deleteNote(id, "","","","")
                    Toast.makeText(this, "Note deleted", Toast.LENGTH_LONG).show()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Check the deletion")
        alert.setView(checkTextView)
        alert.show()
    }
}