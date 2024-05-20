package com.tasks.saved_recipe.presentation

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tasks.domain.room.Recipe
import com.tasks.saved_recipe.R
import com.tasks.saved_recipe.adapter.SavedRecipeRVAdapter
import org.koin.android.ext.android.inject

class SavedRecipeFragment : Fragment() {
    private val viewModel by inject<SavedRecipeViewModel>()
    private lateinit var view: View
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var rv: RecyclerView
    private lateinit var btnAdd: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        view = inflater.inflate(R.layout.fragment_saved_recipe, container, false)

        rv = view.findViewById(R.id.rvRecipes)
        btnAdd = view.findViewById(R.id.ibAdd)

        rv.layoutManager = LinearLayoutManager(this.requireContext())

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        checkAndLoadRecipes()

        viewModel.lunchRecipes()

        viewModel.recipeList.observe(this.requireActivity()) {
            try {
                rv.adapter = createRecipeRVAdapter(it)
            } catch (e: Exception) {
                Log.e("SavedRecipeFragment", e.message.toString())
            }
        }

        btnAdd.setOnClickListener {
            buildAlert(
                title = getString(R.string.create_item),
                hint = getString(R.string.name),
                positiveText = getString(com.tasks.core.R.string.OK),
                actionOnPositive = {
                    if (it.isBlank()) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.empty_title),
                            Toast.LENGTH_LONG
                        ).show()
                        return@buildAlert
                    }
                    viewModel.createRecipe(it)
                },
                negativeText = getString(com.tasks.core.R.string.close),
            )
        }

        return view
    }

    private fun checkAndLoadRecipes() {
        val recipesLoaded = sharedPreferences.getBoolean(RECIPES_WAS_LOADED, false)
        if (!recipesLoaded) {
            viewModel.loadRecipesFromNet { success ->
                if (success) {
                    sharedPreferences.edit().putBoolean(RECIPES_WAS_LOADED, true).apply()
                    viewModel.lunchRecipes()
                }
                else {
                    Toast.makeText(requireContext(),
                        getString(R.string.cant_load_net_recipe), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun createRecipeRVAdapter(recipes: List<Recipe>): SavedRecipeRVAdapter {
        return SavedRecipeRVAdapter(
            items = recipes,
            onItemClick = {
                viewModel.openRecipe(it.id)
            },
            onShareClick = {
                shareRecipe(it)
            },
            onRemoveClick = {
                viewModel.removeRecipe(it.id)
            }
        )
    }

    private fun shareRecipe(recipe: Recipe) {
        val shareText = getString(
            R.string.mask_for_share,
            recipe.name,
            recipe.ingredients,
            recipe.description
        ).trimIndent()

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun buildAlert(
        title: String,
        hint: String,
        text: String = "",
        positiveText: String,
        actionOnPositive: (String) -> Unit,
        negativeText: String? = null,
        actionOnNegative: ((String) -> Unit)? = null,
    ) {
        val dialogView = layoutInflater.inflate(R.layout.set_new_data_in_filed, null)
        val dialogTitle = dialogView.findViewById<TextView>(R.id.title)
        val dialogEdit = dialogView.findViewById<EditText>(R.id.edit)

        dialogTitle.text = title
        dialogEdit.hint = hint
        dialogEdit.setText(text)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton(positiveText) { _, _ -> actionOnPositive(dialogEdit.text.toString()) }

        if (negativeText != null)
            dialog.setNegativeButton(negativeText) { _, _ ->
                if (actionOnNegative != null)
                    actionOnNegative(dialogEdit.text.toString())
            }

        dialog.create().show()
    }

    companion object {
        private const val RECIPES_WAS_LOADED = "recipes_loaded"
    }
}