package com.example.etisalatassessment.features.recipe.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.etisalatassessment.R
import com.example.etisalatassessment.model.Recipes

class RecipeAdapter(private val onLoadData: OnLoadData) :
    ListAdapter<Recipes, RecipeAdapter.RecipeViewHolder>(
        MyDiffUtil()
    ) {

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView
            get() = itemView.findViewById(R.id.recipeImageView)
        val name: TextView
            get() = itemView.findViewById(R.id.nameTextView)
        val proteins: TextView
            get() = itemView.findViewById(R.id.proteinsTextView)
        val calories: TextView
            get() = itemView.findViewById(R.id.caloriesTextView)
        val fats: TextView
            get() = itemView.findViewById(R.id.fatsTextView)
        val headline: TextView
            get() = itemView.findViewById(R.id.headlineTextView)
        val description: TextView
            get() = itemView.findViewById(R.id.descriptionTextView)
        val detailsLayout: LinearLayout
            get() = itemView.findViewById(R.id.detailsLayout)
        val detailsButton: Button
            get() = itemView.findViewById(R.id.details_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(
            R.layout.recipe_item,
            parent,
            false
        )
        return RecipeViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipeItem: Recipes = getItem(position)
        holder.description.text = recipeItem.description
        holder.calories.text = recipeItem.calories
        holder.proteins.text = recipeItem.proteins
        holder.name.text = recipeItem.name
        holder.headline.text = recipeItem.headline
        holder.fats.text = recipeItem.fats
        holder.detailsButton.setOnClickListener {
            toggleContentVisibility(holder.detailsLayout)
        }
        holder.image.setImageBitmap(recipeItem.recipeImage)

    }

    private fun toggleContentVisibility(detailsLayout: LinearLayout) {
        detailsLayout.visibility = if (detailsLayout.visibility == View.VISIBLE) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

}