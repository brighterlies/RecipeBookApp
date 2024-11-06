package com.example.recipebookapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.recipebookapp.R
import com.example.recipebookapp.data.model.RecipeModel
import com.example.recipebookapp.databinding.FragmentNewRecipeBinding
import com.example.recipebookapp.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

class NewRecipeFragment : Fragment() {

    private var _binding: FragmentNewRecipeBinding? = null
    private val binding get() = _binding!!
    private val newRecipeViewModel: RecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewRecipeBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        initUIState()
        initUIListener()
    }

    private fun initUIState() {

    }

    private fun initUIListener() {
        val spinner: Spinner = binding.spinnerCuisine  // Use binding instead of findViewById
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cuisine_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.btnSave.setOnClickListener {
            saveNewRecipe()
            Toast.makeText(requireContext(), "Recipe saved", Toast.LENGTH_LONG).show()
            clearFields()
        }
    }

    private fun saveNewRecipe() {
        lifecycleScope.launch {
            val newRecipe = RecipeModel(
                id = 0,
                title = binding.etTitle.text.toString(),
                description = binding.etDescription.text.toString(),
                cuisine = binding.spinnerCuisine.selectedItem.toString(),
                ingredients = binding.etIngredients.text.toString(),
                directions = binding.etDirections.text.toString(),
                photoUrl = binding.etImg.text.toString(),
                totalTime = 0,
                rating = 0.toString(),
                tags = "",
            )
            newRecipeViewModel.insertRecipe(newRecipe)
        }
    }

    private fun clearFields() {
        binding.etTitle.text?.clear()
        binding.etDescription.text?.clear()
        binding.spinnerCuisine.setSelection(0)
        binding.etIngredients.text?.clear()
        binding.etDirections.text?.clear()
        binding.etTime.text?.clear()
        binding.etImg.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
