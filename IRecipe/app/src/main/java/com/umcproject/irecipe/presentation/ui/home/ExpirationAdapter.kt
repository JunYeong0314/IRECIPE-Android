package com.umcproject.irecipe.presentation.ui.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.ItemExpirationIngredientBinding
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.presentation.util.Util
import com.umcproject.irecipe.presentation.util.Util.getEngResourceId

class ExpirationAdapter(
    private val expirationList: List<Ingredient>,
    private val onClickIngredient: (Ingredient) -> Unit,
    private val isExpirationDetail: Boolean,
    private val context: Context
): RecyclerView.Adapter<ExpirationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpirationAdapter.ViewHolder {
        val binding = ItemExpirationIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = expirationList.size

    override fun onBindViewHolder(holder: ExpirationAdapter.ViewHolder, position: Int) {
        val expiration = expirationList[position]

        holder.setInfo(expiration)
        holder.onClickExpirationCard(expiration)

        if(!isExpirationDetail) holder.setWidthHomeExpirationCard()
    }

    inner class ViewHolder(val binding: ItemExpirationIngredientBinding): RecyclerView.ViewHolder(binding.root){
        fun setInfo(ingredient: Ingredient){
            binding.tvTitle.text = ingredient.name

            ingredient.remainDay?.let { day->
                if(day > 0){
                    binding.tvExpiration.text = "${day}일 남음"
                    if(day < 4) binding.tvExpiration.setTextColor(Color.RED)
                    else binding.tvExpiration.setTextColor(Color.BLACK)
                }else if(day == 0){
                    binding.tvExpiration.text = "오늘만료"
                    binding.tvExpiration.setTextColor(Color.RED)
                }else{
                    binding.tvExpiration.text = "유통기한 만료"
                    binding.tvExpiration.setTextColor(Color.RED)
                }
            }

            val photo = getEngResourceId(ingredient.category)
            photo?.let { binding.ivIngredient.setImageResource(photo) }
        }

        fun setWidthHomeExpirationCard(){
            binding.clExpiration.layoutParams.width = context.resources.getDimension(R.dimen.size_150dp).toInt()
        }

        fun onClickExpirationCard(ingredient: Ingredient){
            binding.clExpiration.setOnClickListener { onClickIngredient(ingredient) }
        }
    }
}