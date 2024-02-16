package com.umcproject.irecipe.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.databinding.ItemHomeBinding
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.domain.model.PostRank
import com.umcproject.irecipe.domain.model.Refrigerator

class HomeAdapter(
//    private val homeList: List<> 쓰고 보니 애매하다 이따 물어보자
//    private val onClickItem: (PostRank) -> Unit
): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val home = homeList[position]
//        home?.let {
//            holder.bind()
//            holder.binding.ibtnDetail.setOnClickListener { onClickDetail(home) }
//        }
    }

    inner class ViewHolder(val binding: ItemHomeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            // home: List<...>
        ) {
//            binding.tvHomeTitle.text = 리스트에서 꺼내서 제목 부여
            binding.rvHome.apply {
                adapter = HomeRankingAdapter(
//                    home, ... onClickItem
                    )
                layoutManager = LinearLayoutManager(binding.rvHome.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }
}