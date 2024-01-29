package com.umcproject.irecipe.presentation.ui.refrigerator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.databinding.ItemIngredientBinding
import com.umcproject.irecipe.domain.model.Ingredient

class RefrigeratorIngredientAdapter(
    private val ingredient: List<Ingredient>
): RecyclerView.Adapter<RefrigeratorIngredientAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemIngredientBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = ingredient.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredient[position]

        holder.setName(ingredient.name)
    }

    inner class ViewHolder(val binding: ItemIngredientBinding): RecyclerView.ViewHolder(binding.root){
        fun setName(name: String){
            binding.tvName.text = name
        }
    }

}