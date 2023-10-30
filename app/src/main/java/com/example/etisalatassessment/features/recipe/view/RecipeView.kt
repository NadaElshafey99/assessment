package com.example.etisalatassessment.features.recipe.view

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.etisalatassessment.R
import com.example.etisalatassessment.features.recipe.viewmodel.RecipeViewModel
import com.example.etisalatassessment.features.recipe.viewmodel.RecipeViewModelFactory
import com.example.etisalatassessment.model.Recipes
import com.example.etisalatassessment.service.repository.RecipeRepositoryImp
import com.example.etisalatassessment.utils.UiState
import kotlinx.coroutines.launch

class RecipeView : AppCompatActivity(), OnLoadData {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var recipeViewModelFactory: RecipeViewModelFactory
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var myAdapter: RecipeAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var recipeImage: Bitmap
    private lateinit var recipesImages: MutableList<Bitmap>
    private var recipes: MutableList<Recipes> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        recipeRecyclerView = findViewById(R.id.recipe_recycler_view)
        progressBar = findViewById(R.id.progressBar)
        recipeImage = BitmapFactory.decodeResource(resources, R.drawable.placeholder)
        recipesImages = mutableListOf()
        myAdapter = RecipeAdapter(this)
        recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        recipeRecyclerView.adapter = myAdapter
        recipeViewModelFactory = RecipeViewModelFactory(RecipeRepositoryImp.getInstance())
        recipeViewModel =
            ViewModelProvider(this, recipeViewModelFactory).get(RecipeViewModel::class.java)
        recipeViewModel.getRecipes()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            recipeViewModel.recipeResponse.collect { recipeState ->
                when (recipeState) {
                    is UiState.Loading -> {
                        showLoadingProgress()
                    }
                    is UiState.Success<*> -> {
                        recipes = ((recipeState.data) as List<Recipes>).toMutableList()
                        for (recipe in recipes) {
                            loadImage(recipe.image)
                        }
                    }
                    is UiState.Error -> {
                        showErrorDialog()
                    }
                }
            }
        }

    }

    private fun showLoadingProgress() {
        progressBar.visibility = View.VISIBLE
        recipeRecyclerView.visibility = View.GONE
    }

    private fun showErrorDialog() {
        progressBar.visibility = View.GONE
        recipeRecyclerView.visibility = View.GONE
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(getString(R.string.error))
        alertDialogBuilder.setMessage(getString(R.string.something_wrong))
        alertDialogBuilder.setPositiveButton(getString(R.string.try_again)) { dialog, _ ->
            showLoadingProgress()
            recipeViewModel.getRecipes()
            dialog.dismiss()
        }

        alertDialogBuilder.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showRecipes() {
        progressBar.visibility = View.GONE
        recipeRecyclerView.visibility = View.VISIBLE
        for (i in 0 until recipes.size) {
            recipes[i].recipeImage = recipesImages[i]
            if (i == recipes.size - 1) {
                myAdapter.submitList(recipes)
            }
        }
    }

    override fun loadImage(imageURL: String?) {
        recipeViewModel.loadImage(imageURL ?: "")
        if (!imageURL.isNullOrEmpty())
            recipeViewModel.imageRecipe.observe(this) {
                recipeImage = it ?: BitmapFactory.decodeResource(resources, R.drawable.placeholder)
                if (!recipesImages.contains(recipeImage)) {
                    recipesImages.add(recipeImage)
                }
                if (recipesImages.size == recipes.size) {
                    showRecipes()
                }
            }

    }
}