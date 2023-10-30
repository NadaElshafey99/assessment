package com.example.etisalatassessment.features.recipe.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.etisalatassessment.service.repository.IRecipeRepository


class RecipeViewModelFactory (private val recipeRepository: IRecipeRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            RecipeViewModel(recipeRepository) as T
        }else{
            throw IllegalArgumentException("ViewModel class is not found!")
        }
    }
}
