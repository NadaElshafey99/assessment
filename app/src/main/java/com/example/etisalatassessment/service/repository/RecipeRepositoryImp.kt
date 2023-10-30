package com.example.etisalatassessment.service.repository

import android.graphics.Bitmap
import com.example.etisalatassessment.service.network.ImageDownloadTask
import com.example.etisalatassessment.service.network.NetworkTask

class RecipeRepositoryImp() : IRecipeRepository {
    companion object {
        private var instance: IRecipeRepository? = null
        fun getInstance(): IRecipeRepository {
            return instance ?: synchronized(this) {
                val temp = RecipeRepositoryImp()
                instance = temp
                temp
            }
        }
    }

    override fun getRecipe(url: String, callback: (String?) -> Unit) {
        val networkTask = NetworkTask { response ->
            callback(response)
        }
        networkTask.execute(url)
    }

    override fun getImageFromNetwork(url: String, callback: (Bitmap?) -> Unit) {
        val imageDownloadTask = ImageDownloadTask { bitmap ->
            if (bitmap != null) {
                callback(bitmap)
          } else {
              callback(null)
            }
        }
        imageDownloadTask.execute(url)
    }
}
