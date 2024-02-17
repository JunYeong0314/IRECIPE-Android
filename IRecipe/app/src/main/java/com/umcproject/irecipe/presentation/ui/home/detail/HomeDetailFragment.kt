package com.umcproject.irecipe.presentation.ui.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentHomeDetailBinding
import com.umcproject.irecipe.domain.model.PostRank
import com.umcproject.irecipe.presentation.ui.community.CommunityViewModel
import com.umcproject.irecipe.presentation.ui.community.post.PostFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util.showVerticalFragment

class HomeDetailFragment(
    private val minPostList: List<PostRank>,
    private val minPostCategoryList: List<PostRank>,
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit,
    private val onHideBottomBar: () -> Unit,
    private val onShowBottomBar: () -> Unit,
    private val onHideTitle: () -> Unit
) : BaseFragment<FragmentHomeDetailBinding>() {
    private val viewModel: CommunityViewModel by viewModels()

    companion object{
        const val TAG = "HomeDetailFragment"
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeDetailBinding {
        return FragmentHomeDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onDestroy() {
        super.onDestroy()
        onHideTitle()
    }
    private fun initView(){
        binding.rvHomeDetail.layoutManager= GridLayoutManager(requireActivity(), 2)
        binding.rvHomeDetail.adapter = HomeDetailAdapter(
            minPostList,
            onClickPost = {
                showVerticalFragment(R.id.fv_main, requireActivity(),
                    PostFragment(onClickBackBtn, it, viewModel, onShowBottomBar),
                    PostFragment.TAG
                )
                onHideBottomBar()
                onClickDetail("커뮤니티")
            }
        )
    }

}