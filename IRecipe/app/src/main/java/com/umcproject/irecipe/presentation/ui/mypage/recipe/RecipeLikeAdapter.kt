package com.umcproject.irecipe.presentation.ui.mypage.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.ItemPostBinding
import com.umcproject.irecipe.domain.model.Post

class RecipeLikeAdapter(private val likeList: List<Post>,
                        private val onClickWrite: (Int) -> Unit):
    RecyclerView.Adapter<RecipeLikeAdapter.ViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecipeLikeAdapter.ViewHolder {
        val binding: ItemPostBinding = ItemPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeLikeAdapter.ViewHolder, position: Int) {
        val post = likeList[position]
        holder.setPost(post)
        post.postId?.let{ holder.onClickPostEvent(it) }
    }

    override fun getItemCount(): Int = likeList.size

    inner class ViewHolder(val binding: ItemPostBinding):RecyclerView.ViewHolder(binding.root){
        fun onClickPostEvent(postId: Int){
            binding.clPost.setOnClickListener { onClickWrite(postId) }
        }
        fun setPost(mpost: Post){
            if(mpost.postImageUrl != null){
                binding.cvImage.visibility = View.VISIBLE
                binding.ivImage.load(mpost.postImageUrl){
                    placeholder(R.drawable.bg_placeholder_gray)
                }
            }else{
                binding.cvImage.visibility = View.GONE
            }

            if(mpost.writerProfileUrl.toString() != ""){
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

            mpost.isLike?.let {
                if(it) binding.ivHeart.setImageResource(R.drawable.ic_heart)
                else binding.ivHeart.setImageResource(R.drawable.ic_heart_empty)
            }
        }
    }
}