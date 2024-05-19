package com.tasks.see_recipe_context.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.tasks.see_recipe_context.R
import org.koin.android.ext.android.inject

class SeeRecipeContextFragment : Fragment() {
    private val viewModel by inject<SeeRecipeContextViewModel>()
    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        view = inflater.inflate(R.layout.fragment_see_recipe_context, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(
            this.requireActivity(),
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {
                    handleBackPressed()
                }
            }
        )

        return view
    }

    private fun handleBackPressed() {
        //TODO алерт на выход
        viewModel.goBack()
    }

}