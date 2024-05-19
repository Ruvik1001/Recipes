package com.tasks.saved_recipe.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tasks.saved_recipe.R
import org.koin.android.ext.android.inject

class SavedRecipeFragment : Fragment() {
    private val viewModel by inject<SavedRecipeViewModel>()
    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        view = inflater.inflate(R.layout.fragment_saved_recipe, container, false)

        return view
    }

}