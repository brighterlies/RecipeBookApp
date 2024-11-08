package com.example.recipebookapp.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipebookapp.R
import com.example.recipebookapp.databinding.FragmentRecipeListBinding
import com.example.recipebookapp.view.adapter.RecipeListAdapter
import com.example.recipebookapp.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class RecipeListFragment : Fragment() {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!
    private val recipeListAdapter: RecipeListAdapter = RecipeListAdapter()
    private val recipeListViewModel: RecipeViewModel by activityViewModels()
    private val newRecipeFragment = NewRecipeFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        initUIState()
        initUIListener()
    }

    private fun initUIState() {
        getRecipes()
        startRecyclerView()
    }

    private fun initUIListener() {
        deleteRecipe()
        binding.fabNewRecipe.setOnClickListener {
            navigateToNewRecipeFragment()
        }
        binding.fabSearch.setOnClickListener {
            binding.etSearch.visibility = View.VISIBLE
        }
        binding.etSearch.setOnClickListener {
            searchRecipe()
        }
        navigateToDetailRecipe()
    }

    private fun startRecyclerView() {
        binding.rvRecipe.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeListAdapter
        }
    }

    private fun getRecipes() {
        lifecycleScope.launch {
            val recipeList = recipeListViewModel.getAllRecipe().value ?: emptyList()
            recipeListAdapter.setRecipeList(recipeList)
        }
    }

    private fun deleteRecipe() {
        recipeListAdapter.onLongClick = {
            lifecycleScope.launch {
                recipeListViewModel.deleteRecipe(it)
                initUI()
                Toast.makeText(requireContext(), "Deleted Recipe", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToNewRecipeFragment() {
        setCurrentFragment(newRecipeFragment)
    }

    private fun navigateToDetailRecipe() {
        recipeListAdapter.onClick = { itemId ->
            val bundle = bundleOf("recipeId" to itemId)
            val recipeDetailFragment = RecipeDetailFragment()
            recipeDetailFragment.arguments = bundle
            setCurrentFragment(recipeDetailFragment)
        }
    }

    private fun searchRecipe() {
        binding.etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(char: CharSequence?, start: Int, before: Int, count: Int) {
                if (char != null) {
                    lifecycleScope.launch {
                        val recipeList = recipeListViewModel.searchRecipe(char.toString()).value ?: emptyList()
                        recipeListAdapter.setRecipeList(recipeList)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setCurrentFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutFragment, fragment)
            commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}