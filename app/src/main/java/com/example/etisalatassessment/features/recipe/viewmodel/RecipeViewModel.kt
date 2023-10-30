package com.example.etisalatassessment.features.recipe.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etisalatassessment.model.Recipes
import com.example.etisalatassessment.service.repository.IRecipeRepository
import com.example.etisalatassessment.utils.Constants
import com.example.etisalatassessment.utils.UiState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeRepository: IRecipeRepository) : ViewModel() {
    private val _recipeResponse = MutableStateFlow<UiState>(UiState.Loading)
    val recipeResponse
        get() = _recipeResponse.asStateFlow()

    private val _imageRecipe = MutableLiveData<Bitmap>()
    val imageRecipe: LiveData<Bitmap>
        get() = _imageRecipe

    fun getRecipes() {
        viewModelScope.launch {
            recipeRepository.getRecipe(Constants.URL) { response ->
                if (response != null) {
                    val gson = Gson()
                    val recipeListType = object : TypeToken<List<Recipes>>() {}.type
                    val recipesList: List<Recipes> = gson.fromJson(response, recipeListType)
                    _recipeResponse.value = UiState.Success(recipesList)
                } else {
                    _recipeResponse.value = UiState.Error("There's something wrong")
                }
            }
        }

    }

    fun loadImage(imageURL: String) {
        recipeRepository.getImageFromNetwork(imageURL) { bitmap ->
            if (bitmap != null) {
                _imageRecipe.value = bitmap
            }
        }
    }
}