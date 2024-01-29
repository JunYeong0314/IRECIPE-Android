package com.umcproject.irecipe.presentation.ui.refrigerator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.databinding.ItemTitleBinding
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.domain.model.Refrigerator

class RefrigeratorTitleAdapter(
    private val refList: List<Refrigerator>
): RecyclerView.Adapter<RefrigeratorTitleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTitleBinding.inflate(
            LayoutInflater.from(parent.context), parent,false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = refList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ref = refList[position]
        holder.setTitle(ref.title)
        holder.bind(ingredient = ref.ingredient)
    }

    inner class ViewHolder(val binding: ItemTitleBinding): RecyclerView.ViewHolder(binding.root){
        fun setTitle(title: String){
            binding.tvTitle.text = title
        }

        fun bind(ingredient: List<Ingredient>){
            binding.rvIngredient.apply {
                adapter = RefrigeratorIngredientAdapter(ingredient)
                layoutManager = LinearLayoutManager(binding.rvIngredient.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }
}