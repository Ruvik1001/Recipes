package com.tasks.see_recipe_context

import com.tasks.domain.room.Recipe

interface SeeRecipeRouter {

    fun goBack(editedRecipe: Recipe? = null)
}