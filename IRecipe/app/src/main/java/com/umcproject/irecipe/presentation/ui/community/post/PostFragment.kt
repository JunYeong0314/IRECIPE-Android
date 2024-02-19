package com.umcproject.irecipe.presentation.ui.community.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentPostBinding
import com.umcproject.irecipe.domain.model.WritePost
import com.umcproject.irecipe.presentation.ui.community.CommunityFragment
import com.umcproject.irecipe.presentation.ui.community.CommunityViewModel
import com.umcproject.irecipe.presentation.ui.community.ModalBottomSheetMyFragment
import com.umcproject.irecipe.presentation.ui.community.comment.CommentFragment
import com.umcproject.irecipe.presentation.ui.community.write.bottomSheet.ModalBottomSheetCategoryFragment
import com.umcproject.irecipe.presentation.ui.home.HomeFragment
import com.umcproject.irecipe.presentation.ui.home.detail.RankDetailFragment
import com.umcproject.irecipe.presentation.ui.mypage.recipe.RecipeWriteFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity
import com.umcproject.irecipe.presentation.util.Util.popFragment
import com.umcproject.irecipe.presentation.util.Util.showVerticalFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment(
    private val onClickBackBtn: (String) -> Unit,
    private val postId: Int,
    private val onShowBottomBar: () -> Unit,
    private val currentScreen: String,
    private val postDeleteCallBack: () -> Unit
) : BaseFragment<FragmentPostBinding>() {
    private val viewModel: CommunityViewModel by viewModels()
    private val postViewModel: PostViewModel by viewModels()

    companion object{
        const val TAG = "PostFragment"
    }
    private var isLike: Boolean? = null

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPostBinding {
        return FragmentPostBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPostInfoFetch(postId)

        // 게시글 단일 조회 성공인 경우
        viewModel.postDetailState.observe(viewLifecycleOwner){
            if(it == 200) initView()
            else Snackbar.make(requireView(), getString(R.string.error_server_fetch_post, it), Snackbar.LENGTH_SHORT).show()
        }

        viewModel.postDetailError.observe(viewLifecycleOwner){
            Snackbar.make(requireView(), getString(R.string.error_fetch_post, it), Snackbar.LENGTH_SHORT).show()
        }

        onClickReview() // Q&A, 리뷰글 버튼 이벤트
        onClickLike() // 게시글 좋아요 버튼 이벤트
        onClickSetting() // 수정, 삭제 버튼 이벤트
    }

    private fun initView(){
        val postInfo = viewModel.getPostInfo()

        postInfo?.let { post->
            binding.tvTitle.text = post.title
            binding.tvName.text = post.writerNick
            binding.tvContent.text = post.content
            binding.tvCategory.text = mapperToCategory(post.category ?: "")
            binding.tvLevel.text = mapperToLevel(post.level ?: "")
            binding.tvContent.text = post.content
            binding.tvHeartCnt.text = post.likes.toString()
            binding.tvCommentCnt.text = post.reviewCount.toString()
            binding.tvStarCnt.text = post.score.toString().substring(0, 3)
        }

        //postInfo?.writerProfileUrl?.let { binding.ivImgProfile.load(it) }
        postInfo?.postImageUrl?.let {
            binding.cvImage.visibility = View.VISIBLE
            binding.ivImage.load(it)
        }

        postInfo?.isLike?.let {
            if(it) binding.ivHeart.setImageResource(R.drawable.ic_heart)
            else binding.ivHeart.setImageResource(R.drawable.ic_heart_empty)
            isLike = it
        }

        postInfo?.myPost?.let {
            if(it) binding.btnPostMy.visibility = View.VISIBLE
            else binding.btnPostMy.visibility = View.GONE
        }
    }
    private fun initLikes(){
        val postInfo = viewModel.getPostInfo()

        postInfo?.isLike?.let {
            if(it) binding.ivHeart.setImageResource(R.drawable.ic_heart)
            else binding.ivHeart.setImageResource(R.drawable.ic_heart_empty)
            isLike = it
        }
        postInfo?.likes?.let {
            binding.tvHeartCnt.text = it.toString()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        when (currentScreen) {
            RecipeWriteFragment.TAG -> {
                //onClickBackBtn("레시피보관함")
            }
            CommunityFragment.TAG -> {
                onClickBackBtn("커뮤니티")
            }
            HomeFragment.TAG -> {
                onClickBackBtn("아이레시피")
            }
        }
        onShowBottomBar()
    }

    private fun onClickReview() {
        binding.llReview.setOnClickListener {
            showVerticalFragment(R.id.fv_main, requireActivity(), CommentFragment(viewModel, postId), CommentFragment.TAG)
        }
    }

    private fun onClickLike(){
        binding.ivHeart.setOnClickListener {
            if (isLike==true) { // 좋아요 해제
                postViewModel.postUnLike(postId)
                postViewModel.unlikeState.observe(viewLifecycleOwner) {state ->
//                    state?.let {
//                        if(state == 200) Snackbar.make(requireView(), "좃앟요 삭제됨", Snackbar.LENGTH_SHORT).show()
//                    }
                }
                binding.ivHeart.setImageResource(R.drawable.ic_heart_empty)
                binding.tvHeartCnt.text = (binding.tvHeartCnt.text.toString().toInt() - 1).toString()

            } else { // 좋아요
                postViewModel.postLike(postId)
                postViewModel.likeState.observe(viewLifecycleOwner) {state ->
                    state?.let {
                        if(state == 200) Snackbar.make(requireView(), getString(R.string.confirm_set_like), Snackbar.LENGTH_SHORT).show()

                    }
                }
                binding.ivHeart.setImageResource(R.drawable.ic_heart)
                binding.tvHeartCnt.text = (binding.tvHeartCnt.text.toString().toInt() + 1).toString()
            }
            isLike = !isLike!!
        }
    }

    private fun onClickSetting(){
        binding.btnPostMy.setOnClickListener {
            val modal = ModalBottomSheetMyFragment(
                postId,
                onShowBottomBar,
                postDeleteCallBack = postDeleteCallBack
            )
            modal.show(childFragmentManager, ModalBottomSheetMyFragment.TAG)
        }
    }

    private fun mapperToCategory(category: String): String {
        return when (category) {
            "KOREAN" -> "한식"
            "WESTERN" -> "양식"
            "JAPANESE" -> "일식"
            "CHINESE" -> "중식"
            "UNIQUE" -> "이색음식"
            "SIMPLE" -> "간편요리"
            "ADVANCED" -> "고급요리"
            else -> "ERROR"
        }
    }
    private fun mapperToLevel(level: String): String {
        return when (level) {
            "DIFFICULT" -> "상"
            "MID" -> "중"
            "EASY" -> "하"
            else -> "ERROR"
        }
    }
}

