package com.umcproject.irecipe.presentation.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.ItemRecipeRankingBinding
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.presentation.util.Util.showHorizontalFragment

class HomeRankingAdapter(

): RecyclerView.Adapter<HomeRankingAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemRecipeRankingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
        fun onItemClickListener(item: Post) {
            binding.clRecipeRanking.setOnClickListener {
                // 해당 레시피로 이동
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}