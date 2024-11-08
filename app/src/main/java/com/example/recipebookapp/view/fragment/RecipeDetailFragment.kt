package com.example.recipebookapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.recipebookapp.R
import com.example.recipebookapp.data.model.RecipeModel
import com.example.recipebookapp.databinding.FragmentRecipeDetailBinding
import com.example.recipebookapp.viewmodel.RecipeViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!
    private val recipeId by lazy { arguments?.getInt("recipeId") ?: -1 }

    private val recipeDetailViewModel: RecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        println("recreational: $recipeId")
        initUI()
        return binding.root
    }

    private fun initUI() {
        initUIState()
        initUIListener()
    }

    private fun initUIState() {
        getRecipe(recipeId)
    }

    private fun initUIListener() {
    }

    private fun getRecipe(id: Int) {
        lifecycleScope.launch {
            if (id != -1) {
                val recipeUI = recipeDetailViewModel.getRecipeById(id).value
                println(recipeUI)
                if (recipeUI != null) {
                    paintUI(recipeUI)
                } else {
                    println("Error: Recipe Data is null")
                }
            } else {
                println("Error: Recipe ID is not valid, Recipe ID is not Found")
            }
        }
    }

    private fun paintUI(recipeModel: RecipeModel) {
        """${recipeModel.totalTime} min""".also {
            binding.tvRecipeTime.text = it
        }
        binding.tvRecipeTitle.text = recipeModel.title
        binding.tvRecipeDescription.text = recipeModel.description
        binding.tvRecipeCuisine.text = recipeModel.cuisine
        binding.tvRecipeIngredients.text = recipeModel.ingredients
        binding.tvRecipeDirections.text = recipeModel.directions
        if (recipeModel.photoUrl.isNotEmpty()) {
            Picasso.get().load(recipeModel.photoUrl).into(binding.imgRecipe)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}