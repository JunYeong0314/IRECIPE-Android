package com.umcproject.irecipe.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.ItemRecipeRankingBinding
import com.umcproject.irecipe.domain.model.PostRank

class RankAdapter(
    private val rankList: List<PostRank>,
    private val onClickRankCard: (Int) -> Unit,
    private val isRankDetail: Boolean,
    private val context: Context
): RecyclerView.Adapter<RankAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = rankList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rankInfo = rankList[position]

        holder.setRankCard(rankInfo.title, position+1, rankInfo.imageUrl, rankInfo.likes, rankInfo.score) // 랭킹 UI 초기화
        rankInfo.postId?.let{ holder.onClickEventRankCard(it) } // 클릭 이벤트

        if(!isRankDetail) holder.setWidthHomeRankCard()
    }

    inner class ViewHolder(val binding: ItemRecipeRankingBinding): RecyclerView.ViewHolder(binding.root){
        fun setRankCard(title: String?, total: Int, imageUrl: String?, likes: Int?, score: Double?){
            with(binding){
                tvRankingTitle.text = title
                tvRankingStarTotal.text = score.toString().substring(0, 3)
                tvLikes.text = "$likes"
                tvRanking.text = "$total"
            }
            imageUrl?.let { binding.ivRecipe.load(it) }
        }

        fun onClickEventRankCard(postId: Int){
            binding.clRecipeRanking.setOnClickListener { onClickRankCard(postId) }
        }

        fun setWidthHomeRankCard(){
            binding.clRecipeRanking.layoutParams.width = context.resources.getDimension(R.dimen.size_150dp).toInt()
        }
    }


}