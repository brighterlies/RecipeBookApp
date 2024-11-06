package com.example.recipebookapp.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.recipebookapp.databinding.ActivityMainBinding
import com.example.recipebookapp.view.fragment.NewRecipeFragment
import com.example.recipebookapp.view.fragment.RecipeDetailFragment
import com.example.recipebookapp.view.fragment.RecipeListFragment
import com.example.recipebookapp.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val newRecipeFragment = NewRecipeFragment()
    private val recipeDetailFragment = RecipeDetailFragment()
    private val recipeListFragment = RecipeListFragment()

    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initUIListener()
        initUIState()
    }

    private fun initUIState() {
        startRecipeListFragment()
    }

    private fun initUIListener() {
        binding.btnAdd.setOnClickListener {
            navigateToNewRecipeFragment()
        }
        binding.btnBack.setOnClickListener {
            startRecipeListFragment()
        }
        binding.ivSearch.setOnClickListener {
            binding.tvTitle.isVisible = false
            binding.etSearch.isVisible = true
            binding.btnBack.isVisible = false
        }
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(char: CharSequence?, start: Int, before: Int, count: Int) {
                if (char != null) {
                    lifecycleScope.launch {
                        recipeViewModel.filterRecipesList(char)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                //
            }
        })
        navigateToDetailRecipe()
    }

    private fun navigateToNewRecipeFragment() {
        val auxTitle = "New Recipe"
        binding.tvTitle.text = auxTitle
        updateUIVisibility(newRecipeFragment)
        setCurrentFragment(newRecipeFragment)
    }

    private fun navigateToDetailRecipe() {
        updateUIVisibility(recipeDetailFragment)
        recipeListFragment.navigateToDetailRecipe()
    }

    private fun startRecipeListFragment() {
        val auxTitle = "Recipe Book App"
        binding.tvTitle.text = auxTitle
        updateUIVisibility(recipeListFragment)
        setCurrentFragment(recipeListFragment)
    }

    private fun searchRecipe() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(char: CharSequence?, start: Int, before: Int, count: Int) {
                if (char != null) {
                    lifecycleScope.launch {
                        recipeViewModel.filterRecipesList(char)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                //
            }
        })
    }

    private fun updateUIVisibility(fragment: Fragment) {
        when (fragment) {
            is NewRecipeFragment -> {
                binding.btnBack.isVisible = true
                binding.btnAdd.isVisible = false
                binding.ivSearch.isVisible = false
                binding.etSearch.isVisible = false
            }

            is RecipeListFragment -> {
                binding.btnBack.isVisible = false
                binding.btnAdd.isVisible = true
                binding.ivSearch.isVisible = true
                binding.etSearch.isVisible = false
            }

            is RecipeDetailFragment -> {
                binding.btnBack.isVisible = true
                binding.btnAdd.isVisible = false
                binding.ivSearch.isVisible = false
                binding.etSearch.isVisible = false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameLayoutFragment.id, fragment)
            commit()
        }
    }
}