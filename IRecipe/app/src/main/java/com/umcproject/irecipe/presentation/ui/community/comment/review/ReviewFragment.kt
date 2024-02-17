package com.umcproject.irecipe.presentation.ui.community.comment.review

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentCommentReviewBinding
import com.umcproject.irecipe.domain.model.PostDetail
import com.umcproject.irecipe.presentation.ui.community.CommunityViewModel
import com.umcproject.irecipe.presentation.ui.community.comment.WriteCommentFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util
import com.umcproject.irecipe.presentation.util.Util.showVerticalFragment

class ReviewFragment(
    private val viewModel: CommunityViewModel,
    private val postId: Int
): BaseFragment<FragmentCommentReviewBinding>() {
    companion object{
        const val TAG = "ReviewFragment"
    }

    init { viewModel.fetchReview(postId) }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommentReviewBinding {
        return FragmentCommentReviewBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 리뷰 요청 State 감지
        viewModel.reviewState.observe(viewLifecycleOwner){
            if(it == 200) initView() // 최초 화면 설정
            else Snackbar.make(requireView(), getString(R.string.error_server_fetch_review, it), Snackbar.LENGTH_SHORT).show()
        }
        viewModel.reviewError.observe(viewLifecycleOwner){ Snackbar.make(requireView(), getString(R.string.error_fetch_review, it), Snackbar.LENGTH_SHORT).show() }

        // 리뷰 콜백 State 감지
        viewModel.postDetailState.observe(viewLifecycleOwner){
            if(it == 200) initScore()
            else Snackbar.make(requireView(), getString(R.string.error_server_fetch_post, it), Snackbar.LENGTH_SHORT).show()
        }
        viewModel.postDetailError.observe(viewLifecycleOwner){ Snackbar.make(requireView(), getString(R.string.error_fetch_post, it), Snackbar.LENGTH_SHORT).show() }

        onClickSetReview() // 후기작성 클릭 이벤트
    }

    private fun initView(){
        val reviewAdapter = ReviewAdapter(reviewList = viewModel.getReview().reversed())
        binding.rvReview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvReview.adapter = reviewAdapter

        initScore() // 점수 text 설정
    }

    private fun initScore(){
        val postInfo = viewModel.getPostInfo()

        postInfo?.let { post->
            binding.tvReviewCnt.text = post.reviewCount.toString()
            post.score?.let {
                binding.tvTotalScore.text = it.toString().substring(0, 3)
                binding.rbReview.rating = it.toFloat()
            }
        }
    }

    private fun onClickSetReview(){
        binding.llWriteReview.setOnClickListener {
            showVerticalFragment(
                R.id.fv_main, requireActivity(),
                WriteCommentFragment(
                    viewModel = viewModel,
                    postId = postId,
                    reviewCallBack = {
                        viewModel.fetchReview(postId)
                        viewModel.getPostInfoFetch(postId)
                    }
                ),
                WriteCommentFragment.TAG
            )
        }
    }
}