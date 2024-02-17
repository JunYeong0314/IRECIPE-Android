package com.umcproject.irecipe.presentation.ui.mypage.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.ItemPostBinding
import com.umcproject.irecipe.domain.model.MyPost
import com.umcproject.irecipe.domain.model.Post

class RecipeWriteAdapter(private val writeList: List<Post>):
    RecyclerView.Adapter<RecipeWriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecipeWriteAdapter.ViewHolder {
        val binding: ItemPostBinding = ItemPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeWriteAdapter.ViewHolder, position: Int) {
        val post = writeList[position]
        holder.setPost(post)
    }

    override fun getItemCount(): Int = writeList.size

    inner class ViewHolder(val binding: ItemPostBinding):RecyclerView.ViewHolder(binding.root){
        fun setPost(mpost: Post){
            if(mpost.postImageUrl != null){
                binding.cvImage.visibility = View.VISIBLE
                binding.ivImage.load(mpost.postImageUrl){
                    placeholder(R.drawable.bg_placeholder_gray)
                }
            }else{
                binding.cvImage.visibility = View.GONE
            }

            if(mpost.writerProfileUrl != null){
                binding.ivImgProfile.load(mpost.writerProfileUrl){
                    placeholder(R.drawable.bg_placeholder_gray)
                }
            }else{
                binding.ivImgProfile.setImageResource(R.drawable.ic_base_profile)
            }

            binding.tvName.text = mpost.writerNick
            binding.tvTime.text = mpost.createdAt
            binding.tvTitle.text = mpost.title
            binding.tvSubtitle.text = mpost.subTitle
            binding.tvHeartCnt.text = mpost.likes.toString()
            binding.tvCommentCnt.text = mpost.reviewCount.toString()
            binding.tvStarCnt.text = mpost.score.toString()
        }
    }
}