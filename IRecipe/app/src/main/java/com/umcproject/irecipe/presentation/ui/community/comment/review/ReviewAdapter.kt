package com.umcproject.irecipe.presentation.ui.community.comment.review

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
            profile = review.profile,
            photo = review.photo,
            name = review.name,
            rating = review.rating,
            date = review.date,
            content = review.content
        )
    }

    inner class ViewHolder(val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root){
        fun setReview(profile: Uri?, photo: Uri?, name: String, rating: Double, date: Date, content: String) {
            profile?.let { binding.ivProfile.setImageURI(it) }
            photo?.let { binding.ivPhoto.setImageURI(it) }
            binding.tvName.text = name
            binding.tvDate.text = date.toString()
            binding.tvContent.text = content

            binding.rbScore.rating = rating.toFloat()
        }

    }
}