package com.tasks.see_recipe_context

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SeeRecipeContextFragment : Fragment() {

    companion object {
        fun newInstance() = SeeRecipeContextFragment()
    }

    private lateinit var viewModel: SeeRecipeContextViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_see_recipe_context, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SeeRecipeContextViewModel::class.java)
        // TODO: Use the ViewModel
    }

}