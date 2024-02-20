package com.umcproject.irecipe.presentation.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.databinding.ItemHomeBinding
import com.umcproject.irecipe.domain.model.Home
import com.umcproject.irecipe.domain.model.HomeType
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.domain.model.PostRank

class HomeAdapter(
    private val homeList: List<Home>,
    private val onClickRankCard: (Int) -> Unit,
    private val onClickRankDetail: () -> Unit,
    private val onClickIngredient: (Ingredient) -> Unit,
    private val onClickIngredientDetail: (List<Ingredient>) -> Unit
): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = homeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val homeType = homeList[position]

        holder.bindTitle(homeType)
        holder.onClickDetail(homeType)
    }

    inner class ViewHolder(val binding: ItemHomeBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindTitle(home: Home){
            when(home.type){
                HomeType.RANK -> {
                    binding.tvHomeTitle.text = "이달의 레시피 랭킹"
                    home.rank?.let { setRankAdapter(home.rank) }
                }
                HomeType.EXPIRATION -> {
                    binding.tvHomeTitle.text = "나의 냉장고 유통기한 임박 재료"
                    home.expiration?.let { setExpirationIngredientAdapter(home.expiration) }
                }
            }
        }

        fun onClickDetail(home: Home){
            binding.ibtnDetail.setOnClickListener {
                when(home.type){
                    HomeType.RANK -> { onClickRankDetail() }
                    HomeType.EXPIRATION -> { onClickIngredientDetail(home.expiration ?: emptyList()) }
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

        private fun setExpirationIngredientAdapter(expirationList: List<Ingredient>){
            binding.rvContent.apply {
                adapter = ExpirationAdapter(expirationList, onClickIngredient = onClickIngredient, false, binding.rvContent.context)
                layoutManager = LinearLayoutManager(binding.rvContent.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }
}