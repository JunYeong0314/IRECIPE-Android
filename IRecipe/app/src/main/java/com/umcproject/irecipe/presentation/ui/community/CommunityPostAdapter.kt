package com.umcproject.irecipe.presentation.ui.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.databinding.ItemPostBinding
import com.umcproject.irecipe.domain.model.Post
import java.util.ArrayList

class CommunityPostAdapter(private val postList: ArrayList<Post>):RecyclerView.Adapter<CommunityPostAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(post: Post)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }
    fun addItem(post: Post) {
        postList.add(post)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CommunityPostAdapter.ViewHolder {
        val binding: ItemPostBinding = ItemPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityPostAdapter.ViewHolder, position: Int) {
        holder.bind(postList[position])
        holder.itemView.setOnClickListener {  mItemClickListener.onItemClick(postList[position]) }
    }

    override fun getItemCount(): Int = postList.size

    inner class ViewHolder(val binding: ItemPostBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(post: Post){ // 서버 연결 때 주석 제거
//            binding.ivImgProfile.setImageResource(post.profileImg)
//            binding.tvName.text = post.name
//            binding.tvTime.text = post.time
//            binding.ivImage.setImageResource(post.postImg)
            binding.tvTitle.text = post.title
            binding.tvSubtitle.text = post.subtitle
//            binding.tvHeartCnt.text = post.heart.toString()
//            binding.tvStarCnt.text = post.star.toString()
//            binding.tvCommentCnt.text = post.comment.toString()
        }
    }
}