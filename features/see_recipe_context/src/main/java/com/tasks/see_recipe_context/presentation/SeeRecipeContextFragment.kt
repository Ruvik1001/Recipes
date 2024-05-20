package com.tasks.see_recipe_context.presentation

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.tasks.core.alertValidateAnyFiled
import com.tasks.core.getCurrentDateTime
import com.tasks.see_recipe_context.R
import com.tasks.see_recipe_context.support.OnBackPressedListener
import org.koin.android.ext.android.inject

class SeeRecipeContextFragment : Fragment(), OnBackPressedListener {
    private val viewModel by inject<SeeRecipeContextViewModel>()
    private lateinit var view: View

    private lateinit var tvName: TextView
    private lateinit var btnEdit: ImageView
    private lateinit var etIngredients: EditText
    private lateinit var etRecipe: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        view = inflater.inflate(R.layout.fragment_see_recipe_context, container, false)

        tvName = view.findViewById(R.id.tvName)
        btnEdit = view.findViewById(R.id.btnEdit)
        etIngredients = view.findViewById(R.id.etIngredients)
        etRecipe = view.findViewById(R.id.etRecipe)

        val recipeId =  requireArguments().getLong(getString(com.tasks.core.R.string.RECIPE_ID))

        viewModel.lunchRecipe(recipeId)

        viewModel.recipe.observe(requireActivity()) {
            tvName.text = it.name
            etIngredients.setText(it.ingredients)
            etRecipe.setText(it.description)
        }

        btnEdit.setOnClickListener {
            buildAlert(
                title = getString(R.string.rename),
                hint = getString(R.string.new_name),
                text = tvName.text.toString(),
                positiveText = getString(R.string.confirm),
                actionOnPositive = {
                    if (it.isBlank()) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.empty_title),
                            Toast.LENGTH_LONG
                        ).show()
                        return@buildAlert
                    }
                    val recipe = viewModel.recipe.value!!
                    recipe.name = it
                    recipe.ingredients = etIngredients.text.toString()
                    recipe.description = etRecipe.text.toString()
                    viewModel.updateLocal(recipe)
                },
                negativeText = getString(com.tasks.core.R.string.close),
            )
        }

        return view
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

    override fun onBackPressed() {
        if (!alertValidateAnyFiled(
                context = requireContext(),
                value = tvName.text.toString(),
                reflectFunction = { value: String -> value.isNotBlank() },
                badReflectString = getString(R.string.empty_title)
            ))
            return
        val recipeBeforeEdit = viewModel.recipe.value!!
        if (recipeBeforeEdit.name == tvName.text.toString() &&
            recipeBeforeEdit.ingredients == etIngredients.text.toString() &&
            recipeBeforeEdit.description == etRecipe.text.toString())
        {
            viewModel.goBack(recipeBeforeEdit)
            return
        }
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.exit))
            .setMessage(getString(R.string.save_changes))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                val recipe = viewModel.recipe.value!!
                recipe.name = tvName.text.toString()
                recipe.ingredients = etIngredients.text.toString()
                recipe.description = etRecipe.text.toString()
                recipe.timestamp = getCurrentDateTime()
                viewModel.goBack(recipe)
            }
            .setNegativeButton(getString(R.string.no)) { _, _ -> viewModel.goBack() }
            .setNeutralButton(getString(com.tasks.core.R.string.close)) { _, _ -> return@setNeutralButton }
            .create().show()
    }

}