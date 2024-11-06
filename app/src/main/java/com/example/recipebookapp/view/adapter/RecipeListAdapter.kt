package com.example.recipebookapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebookapp.data.model.RecipeModel
import com.example.recipebookapp.databinding.ItemRecipeListBinding
import com.squareup.picasso.Picasso

class RecipeListAdapter(
    private var recipeList: List<RecipeModel> = emptyList()
) : RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {

    var onClick: ((Int) -> Unit)? = null
    var onLongClick: ((RecipeModel) -> Unit)? = null

    inner class RecipeViewHolder(private val binding: ItemRecipeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(recipeModel: RecipeModel) {
            val recipeId = recipeModel.id.toInt()
            binding.tvRecipeTitle.text = recipeModel.title
            if(recipeModel.description.isNotEmpty()) {
                binding.tvRecipeDescription.text = String.format("%s...", recipeModel.description)
            }
            if (recipeModel.photoUrl.isNotEmpty()) {
                Picasso.get().load(recipeModel.photoUrl).into(binding.imgRecipe)
            }
            binding.itemRecipe.setOnClickListener {
                onClick?.invoke(recipeId)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeListAdapter.RecipeViewHolder {
        val binding = ItemRecipeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeListAdapter.RecipeViewHolder, position: Int) {
        holder.onBind(recipeList[position])
    }

    override fun getItemCount() = recipeList.size

    fun setRecipeList(recipeResponse: List<RecipeModel>) {
        recipeList = recipeResponse
        notifyDataSetChanged()
    }
}