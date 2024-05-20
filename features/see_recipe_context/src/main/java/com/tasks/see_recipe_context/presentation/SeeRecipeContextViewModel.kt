package com.tasks.see_recipe_context.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tasks.domain.room.DB
import com.tasks.domain.room.DBProvider
import com.tasks.domain.room.Recipe
import com.tasks.see_recipe_context.SeeRecipeRouter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeeRecipeContextViewModel(
    applicationContext: Context,
    private val seeRecipeRouter: SeeRecipeRouter
) : ViewModel() {
    private var db: DB

    init {
        db = DBProvider.getDB(applicationContext)
    }

    private val mRecipe = MutableLiveData<Recipe>(Recipe(
        name = "",
        description = "",
        ingredients = "",
        timestamp = "",
    )
    )
    val recipe: LiveData<Recipe> = mRecipe


    fun lunchRecipe(recipeId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val tRecipe = db.recipeDao().getById(recipeId)
            withContext(Dispatchers.Main) { mRecipe.postValue(tRecipe!!) }
        }
    }

    fun updateLocal(updRecipe: Recipe) {
        CoroutineScope(Dispatchers.IO).launch {
            mRecipe.postValue(updRecipe)
        }
    }

    fun goBack(updRecipe: Recipe? = null) {
        seeRecipeRouter.goBack(updRecipe)
    }
}