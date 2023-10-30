package com.example.etisalatassessment.service.repository

import android.graphics.Bitmap

interface IRecipeRepository {
    fun getRecipe(url: String, callback: (String?) -> Unit)
    fun getImageFromNetwork(url: String, callback: (Bitmap?) -> Unit)

}