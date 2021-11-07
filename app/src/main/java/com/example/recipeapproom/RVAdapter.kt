package com.example.recipeapproom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapproom.database.RecipeEntity
import com.example.recipeapproom.databinding.ItemRowBinding


class RVAdapter(
    private val activity: ViewRecipes,
    private val items: List<RecipeEntity>
) : RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RVAdapter.ItemViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            tvNote.text =
                "Title: ${item.title} \n" +
                        "Auther: ${item.author} \n" +
                        "Ingredients: ${item.ingredients} \n" +
                        "Instruction: ${item.instruction} \n\n"


            ibEditNote.setOnClickListener {
                activity.raiseDialog(item.id)

            }
            ibDeleteNote.setOnClickListener {
                activity.checkDeleteDialog(item.id)
            }
        }
    }

    override fun getItemCount() = items.size
}