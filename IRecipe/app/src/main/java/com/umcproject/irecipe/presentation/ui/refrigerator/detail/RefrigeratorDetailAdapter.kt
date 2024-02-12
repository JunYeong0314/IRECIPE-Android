package com.umcproject.irecipe.presentation.ui.refrigerator.detail

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.ItemIngredientBinding
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.presentation.util.Util

class RefrigeratorDetailAdapter(
    private val ingredientList: List<Ingredient>,
    private val onClickIngredient: (Ingredient) -> Unit
): RecyclerView.Adapter<RefrigeratorDetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemIngredientBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = ingredientList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredientList[position]
        val name = ingredientList[position].name

        holder.setName(name)
        holder.onClickIngredientEvent(ingredient) // 재료 클릭이벤트
    }

    inner class ViewHolder(val binding: ItemIngredientBinding): RecyclerView.ViewHolder(binding.root){
        fun setName(name: String){
            binding.tvName.text = name
        }

        fun onClickIngredientEvent(ingredient: Ingredient){
            binding.llIngredient.setOnClickListener { onClickIngredient(ingredient) }
        }
    }

}