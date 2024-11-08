package com.example.recipebookapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.recipebookapp.databinding.ActivityMainBinding
import com.example.recipebookapp.view.fragment.RecipeListFragment

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