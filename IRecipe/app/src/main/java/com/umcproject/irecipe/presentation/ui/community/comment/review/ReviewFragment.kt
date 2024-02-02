package com.umcproject.irecipe.presentation.ui.community.comment.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umcproject.irecipe.databinding.FragmentCommentReviewBinding
import com.umcproject.irecipe.domain.model.mockReviewList
import com.umcproject.irecipe.presentation.util.BaseFragment

class ReviewFragment: BaseFragment<FragmentCommentReviewBinding>() {
    companion object{
        const val TAG = "ReviewFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommentReviewBinding {
        return FragmentCommentReviewBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView(){
        val reviewAdapter = ReviewAdapter(reviewList = mockReviewList)
        binding.rvReview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvReview.adapter = reviewAdapter
    }
}