package com.example.etisalatassessment.model

import android.graphics.Bitmap

data class Recipes(
        val calories: String?,
        val carbos: String?,
        val description: String?,
        val difficulty: Int?,
        val fats: String?,
        val headline: String?,
        val id: String?,
        val image: String?,
        val name: String?,
        val proteins: String?,
        val thumb: String?,
        val time: String?,
        var recipeImage: Bitmap?
)