package com.umcproject.irecipe.presentation.ui.community.comment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentCommentWriteBinding
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.presentation.ui.community.CommunityViewModel
import com.umcproject.irecipe.presentation.ui.community.comment.review.ReviewFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util
import com.umcproject.irecipe.presentation.util.Util.popFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteCommentFragment(
    private val viewModel: CommunityViewModel,
    private val postId: Int,
    private val reviewCallBack: () -> Unit
): BaseFragment<FragmentCommentWriteBinding>() {

    // 이미지 콜백 변수
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.cvImage.visibility = View.VISIBLE
            binding.ivImage.setImageURI(uri)
            viewModel.setReviewImage(uri = uri)
        }
    }

    companion object{
        const val TAG = "WriteCommentFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommentWriteBinding {
        return FragmentCommentWriteBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 별점, 후기에 따른 완료 버튼 state 설정
        viewModel.isReviewComplete.observe(viewLifecycleOwner) { binding.btnComplete.isEnabled = it }

        initView()
        scoreObserve() // 점수 감지 함수
        contentObserve() // 내용 감지 함수
        onClickImage() // 이미지 업로드 함수
        onClickComplete() // 작성 완료 함수
    }

    private fun initView(){
        binding.rbRating.visibility = View.VISIBLE

        viewModel.getPostInfo()?.let { post->
            binding.tvTitle.text = post.title
            post.postImageUrl?.let {
                binding.ivPhoto.visibility = View.VISIBLE
                binding.ivPhoto.load(it)
            }
        }
    }

    private fun scoreObserve(){
        binding.rbRating.setOnRatingBarChangeListener{ _, rating, fromUser ->
            if(fromUser) viewModel.setScore(rating.toInt())
        }
    }

    private fun contentObserve(){
        binding.etContent.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(content: Editable?) {
                if(content.isNullOrEmpty()) viewModel.setReviewContent("")
                else viewModel.setReviewContent(binding.etContent.text.toString())
            }
        })
    }

    private fun onClickImage(){
        binding.llBtnPhoto.setOnClickListener { imageDialog() }
    }

    private fun imageDialog(){
        val array = arrayOf(
            getString(R.string.profile_pickAlbum),
            getString(R.string.common_delete),
            getString(R.string.profile_cancel)
        )

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.profile_title))
            .setItems(array
            ) { _, which ->
                when(which){
                    0 -> { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
                    1 -> {
                        viewModel.setReviewImage(null)
                        binding.cvImage.visibility = View.GONE
                    }
                    2 -> {}
                }
            }
            .show()
    }

    private fun onClickComplete(){
        binding.btnComplete.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.setReview(requireContext(), postId).collect{ state->
                    when(state){
                        is State.Loading -> {}
                        is State.Success -> {
                            Snackbar.make(requireView(), getString(R.string.confirm_add_review), Snackbar.LENGTH_SHORT).show()
                            reviewCallBack()
                            popFragment(requireActivity())
                        }
                        is State.ServerError -> { Snackbar.make(requireView(), getString(R.string.error_server_review, state.code), Snackbar.LENGTH_SHORT).show() }
                        is State.Error -> { Snackbar.make(requireView(), getString(R.string.error_review, state.exception.message), Snackbar.LENGTH_SHORT).show() }
                    }
                }
            }

        }
    }
}