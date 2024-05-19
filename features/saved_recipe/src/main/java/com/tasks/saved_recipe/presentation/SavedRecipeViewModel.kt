package com.tasks.saved_recipe.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tasks.domain.api.RetrofitClient
import com.tasks.domain.room.DB
import com.tasks.domain.room.DBProvider
import com.tasks.domain.room.Recipe
import com.tasks.saved_recipe.SavedRecipeRouter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SavedRecipeViewModel(
    private val applicationContext: Context,
    private val savedRecipeRouter: SavedRecipeRouter
) : ViewModel() {
    private lateinit var db: DB
    private val mRecipeList = MutableLiveData<List<Recipe>>(listOf())
    val recipeList: LiveData<List<Recipe>> = mRecipeList

    init {
        CoroutineScope(Dispatchers.IO).launch {
            db = DBProvider.getDB(applicationContext)
        }
    }

    fun loadRecipesFromNet(success: (Boolean)->Unit) {
        val call = RetrofitClient.apiService.getRecipes()
        call.enqueue(object : Callback<List<Recipe>> {
            override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                if (response.isSuccessful) {
                    val recipes = response.body()
                    if (recipes != null) {
                        CoroutineScope(Dispatchers.IO).launch {
                            recipes.forEach {
                                db.recipeDao().insert(it)
                            }
                            withContext(Dispatchers.Main) { success(true) }
                        }
                    }
                } else {
                    Log.e(
                        "SavedRecipeViewModel:loadRecipesFromNet",
                        "Error: ${response.errorBody()?.string()}"

                    )
                    success(false)
                }
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                Log.e(
                    "SavedRecipeViewModel:loadRecipesFromNet",
                    "Failed: ${t.message}"
                )
                success(false)
            }
        })
    }

    fun lunchRecipes() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = db.recipeDao().getAll()
            withContext(Dispatchers.Main) {
                mRecipeList.postValue(list)
            }
        }
    }

    fun openRecipe(recipeId: Long) {
        savedRecipeRouter.goToSeeRecipe(recipeId)
    }
}