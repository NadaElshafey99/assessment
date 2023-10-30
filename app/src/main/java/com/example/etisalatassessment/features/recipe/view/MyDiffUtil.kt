package com.example.etisalatassessment.features.recipe.view

import androidx.recyclerview.widget.DiffUtil
import com.example.etisalatassessment.model.Recipes

class MyDiffUtil : DiffUtil.ItemCallback<Recipes>() {
    override fun areItemsTheSame(oldItem: Recipes, newItem: Recipes): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Recipes, newItem: Recipes): Boolean {
        return oldItem == newItem
    }

}