package com.umcproject.irecipe.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.databinding.ItemHomeBinding
import com.umcproject.irecipe.domain.model.Home
import com.umcproject.irecipe.domain.model.HomeType
import com.umcproject.irecipe.domain.model.PostRank

class HomeAdapter(
    private val homeList: List<Home>,
    private val onClickRankCard: (Int) -> Unit,
    private val onClickRankDetail: () -> Unit,
    private val context: Context
): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = homeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val homeType = homeList[position]

        holder.bind(homeType)
        holder.onClickDetail(homeType.type)
    }

    inner class ViewHolder(val binding: ItemHomeBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(home: Home){
            if(home.rank != null){
                binding.tvHomeTitle.text = "이달의 레시피 랭킹"
                setRankAdapter(home.rank)
            }
        }

        fun onClickDetail(homeType: HomeType){
            binding.ibtnDetail.setOnClickListener {
                when(homeType){
                    HomeType.RANK -> { onClickRankDetail() }
                    else -> {}
                }
            }
        }

        private fun setRankAdapter(rankList: List<PostRank>){
            binding.rvContent.apply {
                adapter = RankAdapter(rankList, onClickRankCard, false, context)
                layoutManager = LinearLayoutManager(binding.rvContent.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }
}