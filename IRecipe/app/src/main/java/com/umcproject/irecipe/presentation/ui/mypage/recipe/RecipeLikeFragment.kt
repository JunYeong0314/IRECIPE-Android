package com.umcproject.irecipe.presentation.ui.mypage.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentMypageLikeBinding
import com.umcproject.irecipe.presentation.ui.community.CommunityViewModel
import com.umcproject.irecipe.presentation.ui.community.post.PostFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeLikeFragment(
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit,
    private val onHideBottomBar: () -> Unit,
    private val onShowBottomBar: () -> Unit
) : BaseFragment<FragmentMypageLikeBinding>() {
    private val viewModel: RecipeViewModel by viewModels()
    private val cViewModel: CommunityViewModel by viewModels()
    private var selectSortType = "기본순"
    companion object{
        const val TAG = "RecipeLikeFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMypageLikeBinding {
        return FragmentMypageLikeBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchLike(0)

        viewModel.myLikeState.observe(viewLifecycleOwner){
            val postList = viewModel.getMyLikeList()

            if(it ==200 && postList.isEmpty()){
                binding.llRecipeLike.visibility = View.GONE
                binding.rvRecipeLike.visibility = View.VISIBLE
            }
            if(it == 200) initView()
            else Snackbar.make(requireView(), "관심있는 글이 없습니다!", Snackbar.LENGTH_SHORT).show()
        }

        viewModel.myLikeError.observe(viewLifecycleOwner){
            Snackbar.make(requireView(), getString(R.string.error_fetch_post, it), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        binding.llRecipeLike.visibility = View.GONE
        binding.rvRecipeLike.visibility = View.VISIBLE
        val myLikeList = viewModel.getMyLikeList()
        val myLikeAdapter = RecipeLikeAdapter(myLikeList,
            onClickWrite = {
                Util.showHorizontalFragment(
                    R.id.fv_main, requireActivity(),
                    PostFragment(onClickBackBtn, it, onShowBottomBar, RecipeWriteFragment.TAG, postDeleteCallBack = {viewModel.fetchWrite(0)}, postUpdateCallBack = {cViewModel.fetchPost(0, selectSortType)}),
                    PostFragment.TAG
                )
                onHideBottomBar()
                onClickDetail("레시피 보관함")
            })
        binding.rvRecipeLike.adapter = myLikeAdapter
        binding.rvRecipeLike.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}