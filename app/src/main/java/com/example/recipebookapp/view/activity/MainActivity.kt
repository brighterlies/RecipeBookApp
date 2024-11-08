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
    private val recipeListFragment = RecipeListFragment()

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
        binding.ivHome.setOnClickListener {
            startRecipeListFragment()
        }
    }

    private fun startRecipeListFragment() {
        setCurrentFragment(recipeListFragment)
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameLayoutFragment.id, fragment)
            commit()
        }
    }
}