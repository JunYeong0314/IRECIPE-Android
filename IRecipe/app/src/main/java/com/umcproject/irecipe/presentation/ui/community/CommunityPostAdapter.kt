package com.umcproject.irecipe.presentation.ui.community

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.ItemPostBinding
import com.umcproject.irecipe.domain.model.Post

class CommunityPostAdapter(
    private val postList: List<Post>
):RecyclerView.Adapter<CommunityPostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CommunityPostAdapter.ViewHolder {
        val binding: ItemPostBinding = ItemPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityPostAdapter.ViewHolder, position: Int) {
        val post = postList[position]

        holder.setPost(post)
    }

    override fun getItemCount(): Int = postList.size

    inner class ViewHolder(val binding: ItemPostBinding):RecyclerView.ViewHolder(binding.root){
        fun setPost(post: Post){
            if(post.postImageUrl != null){
                binding.cvImage.visibility = View.VISIBLE
                binding.ivImage.load(post.postImageUrl){
                    placeholder(R.drawable.bg_placeholder_gray)
                }
            }

            if(post.writerProfileUrl != null){
                binding.ivImgProfile.load(post.writerProfileUrl){
                    placeholder(R.drawable.bg_placeholder_gray)
                }
            }

            binding.tvName.text = post.writerNick
            binding.tvTitle.text = post.title
            binding.tvSubtitle.text = post.subTitle
            binding.tvHeartCnt.text = post.likes.toString()
            binding.tvCommentCnt.text = post.reviewCount.toString()
            binding.tvStarCnt.text = post.score.toString()

            post.isLike?.let {
                if(it) binding.ivHeart.setImageResource(R.drawable.ic_heart)
                else binding.ivHeart.setImageResource(R.drawable.ic_heart_empty)
            }
        }
    }
}