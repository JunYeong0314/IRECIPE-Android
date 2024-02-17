package com.umcproject.irecipe.presentation.ui.community.comment.review

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.umcproject.irecipe.databinding.ItemReviewBinding
import com.umcproject.irecipe.domain.model.Review
import java.util.Date

class ReviewAdapter(
    private val reviewList: List<Review>
): RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = reviewList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = reviewList[position]

        holder.setReview(
            profileUrl = review.writerProfile,
            nick = review.writerNick,
            content = review.content,
            score = review.score,
            imageUrl = review.imageUrl,
            createdAt = review.createdAt
        )
    }

    inner class ViewHolder(val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root){
        fun setReview(nick: String?, profileUrl: String?, content: String?, score: Int?, imageUrl: String?, createdAt: String?) {
            //profileUrl?.let { binding.ivProfile.load(it) }
            imageUrl?.let {
                binding.ivPhoto.visibility = View.VISIBLE
                binding.ivPhoto.load(it)
            }
            binding.tvName.text = nick
            binding.tvDate.text = createdAt?.substring(0, 10)
            binding.tvContent.text = content
            binding.rbScore.rating = score?.toFloat()!!
        }

    }
}