package com.umcproject.irecipe.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentHomeBinding
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.domain.model.Refrigerator
import com.umcproject.irecipe.presentation.ui.community.post.PostFragment
import com.umcproject.irecipe.presentation.ui.home.detail.HomeDetailFragment
import com.umcproject.irecipe.presentation.ui.refrigerator.detail.RefrigeratorDetailFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util
import com.umcproject.irecipe.presentation.util.Util.showHorizontalFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment(
    private val onHideTitle: () -> Unit,
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit
): BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by viewModels()

    companion object{
        const val TAG = "HomeFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.fetchState.observe(viewLifecycleOwner) {state ->
//            state?.let {
//                if (state==200) initView()
//            }
//        }
        initView()
    }

    private fun initView() {
        binding.ibtnDetail.setOnClickListener {
            onClickDetail("이달의 레시피 랭킹")
            showHorizontalFragment(
                R.id.fv_main,
                requireActivity(),
                HomeDetailFragment(onClickDetail, onClickBackBtn,onHideTitle),
                HomeDetailFragment.TAG
            )

        }
//        binding.rvHome.apply {
//            adapter = HomeRankingAdapter(
////                    home, ... onClickItem
//            )
//            layoutManager = LinearLayoutManager(binding.rvHome.context, LinearLayoutManager.HORIZONTAL, false)
//        }


        // 리사이클러뷰로 구현할 때
//        val homeDatas = listOf(
//            viewModel.getPostRank()
//        )

//        val homeAdapter = HomeAdapter(
//            homeDatas = homeDatas,
//            onClickDetail = {goDetailPage()}
//            onClickItem = { showVerticalFragment(R.id.fv_main, requireActivity(),PostFragment(onClickBackBtn, )) } 포스트 호출 후 구현
//        )
//        binding.rvHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        binding.rvHome.adapter = homeAdapter
    }
//    private fun goDetailPage(){
//        onClickDetail("이달의 레시피 랭킹")
//        Util.showHorizontalFragment(
//            R.id.fv_main,
//            requireActivity(),
//            HomeDetailFragment(),
//            HomeDetailFragment.TAG
//        )
//    }

}