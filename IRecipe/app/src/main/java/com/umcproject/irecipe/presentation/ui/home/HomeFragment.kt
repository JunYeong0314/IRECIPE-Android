package com.umcproject.irecipe.presentation.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.viewpager2.widget.ViewPager2
import com.umcproject.irecipe.domain.model.PostRank
import com.umcproject.irecipe.presentation.ui.community.CommunityViewModel
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseFirstFragment
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseFourthFragment
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseSecondFragment
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseThirdFragment
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseVpAdapter
import com.umcproject.irecipe.presentation.util.MainActivity
import com.umcproject.irecipe.presentation.util.Util.showVerticalFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class HomeFragment(
    private val onHideTitle: () -> Unit,
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit,
    private val onHideBottomBar: () -> Unit,
    private val onShowBottomBar: () -> Unit
): BaseFragment<FragmentHomeBinding>() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val communityViewModel: CommunityViewModel by viewModels()

//    private var homeDatas = ListOf(
        // 이달의 레시피 랭킹    이따 어케묶지 있는지 물어보자 아니 근데
        // 나의 냉장고 유통기한 )

    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())
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
        advertiseView()
    }

    private fun initView() {
        val minPostList: List<PostRank> = homeViewModel.getPostRank() // 사이즈가 0으로 뜸;;
        val minPostCategoryList: List<PostRank> = homeViewModel.getPostRankCategory()
        binding.ibtnDetail.setOnClickListener {
            onClickDetail("이달의 레시피 랭킹")
            showHorizontalFragment(
                R.id.fv_main, requireActivity(),
                HomeDetailFragment(minPostList, minPostCategoryList,onClickDetail, onClickBackBtn,onHideBottomBar,onShowBottomBar,onHideTitle),
                HomeDetailFragment.TAG
            )
        }
        val postList = homeViewModel.getPostRank()
        binding.rvHome.apply {
            adapter = HomeRankingAdapter(
                    postList,
                onClickPost = { // 게시글 클릭 콜백 함수
                    showHorizontalFragment(
                        R.id.fv_main, requireActivity(),
                        PostFragment(onClickBackBtn, it, communityViewModel, onShowBottomBar),
                        PostFragment.TAG
                    )
                    onHideBottomBar()
                    onClickDetail("커뮤니티")
                }
            )
            layoutManager = LinearLayoutManager(binding.rvHome.context, LinearLayoutManager.HORIZONTAL, false)
        }


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

    private fun advertiseView(){ //광고배너 화면
        val advertiseAdapter = AdvertiseVpAdapter(this)
        advertiseAdapter.addFragment(AdvertiseFirstFragment())
        advertiseAdapter.addFragment(AdvertiseSecondFragment())
        advertiseAdapter.addFragment(AdvertiseThirdFragment(onClickDetail, onClickBackBtn))
        advertiseAdapter.addFragment(AdvertiseFourthFragment())

        binding.vpAd.adapter = advertiseAdapter
        binding.vpAd.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.homeIndicator.setViewPager(binding.vpAd)

        autoSlide(advertiseAdapter)//자동 슬라이드
    }

    private fun autoSlide(adapter: AdvertiseVpAdapter) {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post {
                    val nextItem = binding.vpAd.currentItem + 1
                    if (nextItem < adapter.itemCount) {
                        binding.vpAd.currentItem = nextItem
                    } else {
                        binding.vpAd.currentItem = 0 // 순환
                    }
                }
            }
        }, 3000, 3000)
    }
    private fun changeTop(){
        val mainActivity = activity as? MainActivity
        mainActivity?.binding?.tvTitle?.text = ""
        mainActivity?.binding?.ibtnBack?.visibility = View.GONE
        mainActivity?.binding?.btmMain?.visibility = View.VISIBLE
    }
}