package com.umcproject.irecipe.presentation.ui.home.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.ItemRecipeRankingBinding
import com.umcproject.irecipe.domain.model.PostRank

class HomeDetailAdapter(
    private val minPostList: List<PostRank>,
    private val onClickPost: (Int) -> Unit
) : RecyclerView.Adapter<HomeDetailAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = minPostList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val minPost = minPostList[position]
        val postId = minPost.postId
        postId.let{holder.onClickPostEvent(it)}

        holder.bind(minPost)
        holder.rankingBind(position)
    }
    inner class ViewHolder(val binding: ItemRecipeRankingBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(minPost: PostRank) {
            binding.tvRankingTitle.text = minPost.title
            binding.tvRankingStarTotal.text = minPost.scoresInOneMonth.toString()
            binding.tvRankingCnt.text = minPost.likes.toString()

            if (minPost.imageUrl!= null){
                binding.ivRecipe.load(minPost.imageUrl){
                    placeholder(R.drawable.bg_placeholder_gray)
                }
            }
        }
        fun rankingBind(rank: Int){
            binding.tvRanking.text = rank.toString()
        }
        fun onClickPostEvent(postId: Int){
            binding.clRecipeRanking.setOnClickListener { onClickPost(postId) }
        }

    }
}