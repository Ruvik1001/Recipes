package com.tasks.saved_recipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tasks.domain.room.Recipe
import com.tasks.saved_recipe.R

class SavedRecipeRVAdapter(
    private val items: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit,
    private val onShareClick: (Recipe) -> Unit,
    private val onRemoveClick: (Recipe) -> Unit,
): RecyclerView.Adapter<SavedRecipeRVAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.nameFragment)
        val btnShare: ImageView = view.findViewById(R.id.btnShare)
        val btnRemove: ImageView = view.findViewById(R.id.btnRemove)
        val tvTimestamp: TextView = view.findViewById(R.id.tvTimestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipes_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = items[position].name
        holder.tvTitle.setOnClickListener { onItemClick(items[position]) }
        holder.btnShare.setOnClickListener { onShareClick(items[position]) }
        holder.btnRemove.setOnClickListener { onRemoveClick(items[position]) }
        holder.tvTimestamp.text = items[position].timestamp
    }

}