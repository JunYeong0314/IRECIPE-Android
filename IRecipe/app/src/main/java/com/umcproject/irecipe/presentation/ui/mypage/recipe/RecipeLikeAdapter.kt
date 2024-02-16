package com.umcproject.irecipe.presentation.ui.mypage.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.ItemPostBinding
import com.umcproject.irecipe.domain.model.MyPost

class RecipeLikeAdapter(private val likeList: List<MyPost>)
    : RecyclerView.Adapter<RecipeLikeAdapter.ViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecipeLikeAdapter.ViewHolder {
        val binding: ItemPostBinding = ItemPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeLikeAdapter.ViewHolder, position: Int) {
        val post = likeList[position]
        holder.setPost(post)
    }

    override fun getItemCount(): Int = likeList.size

    inner class ViewHolder(val binding: ItemPostBinding):RecyclerView.ViewHolder(binding.root){
        fun setPost(mpost: MyPost){
            if(mpost.imageUrl != null){
                binding.cvImage.visibility = View.VISIBLE
                binding.ivImage.load(mpost.imageUrl){
                    placeholder(R.drawable.bg_placeholder_gray)
                }
            }else{
                binding.cvImage.visibility = View.GONE
            }

//            if(profile != null){
//                binding.ivImgProfile.load(profile){
//                    placeholder(R.drawable.bg_placeholder_gray)
//                }
//            }else{
//                binding.ivImgProfile.setImageResource(R.drawable.ic_base_profile)
//            }

            //binding.tvName.text = nick
            //binding.tvTime
            binding.tvTitle.text = mpost.title
            binding.tvSubtitle.text = mpost.subTitle
            binding.tvHeartCnt.text = mpost.likes.toString()
            //binding.tvCommentCnt.text = mpost.reviewCount.toString()
            binding.tvStarCnt.text = mpost.score.toString()
        }
    }
}