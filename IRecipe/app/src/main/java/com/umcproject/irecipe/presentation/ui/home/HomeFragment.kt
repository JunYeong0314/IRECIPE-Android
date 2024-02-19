package com.umcproject.irecipe.presentation.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentHomeBinding
import com.umcproject.irecipe.domain.model.Home
import com.umcproject.irecipe.domain.model.HomeType
import com.umcproject.irecipe.presentation.ui.community.post.PostFragment
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseFirstFragment
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseFourthFragment
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseSecondFragment
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseThirdFragment
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseVpAdapter
import com.umcproject.irecipe.presentation.ui.home.detail.RankDetailFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util.showHorizontalFragment
import com.umcproject.irecipe.presentation.util.Util.showVerticalFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class HomeFragment(
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit,
    private val onHideBottomBar: () -> Unit,
    private val onShowBottomBar: () -> Unit
): BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by viewModels()
    private var currentPage = 0

    companion object{
        const val TAG = "HomeFragment"
        const val DELAY_MS: Long = 3000 // 슬라이드 간 딜레이
        const val PERIOD_MS: Long = 2000 // 자동 슬라이드 주기
        const val NUM_PAGES = 4
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchRank(0)

        // 레시피 랭킹 state
        viewModel.firstRankList.observe(viewLifecycleOwner) { if(!it.isNullOrEmpty()) initView() }
        viewModel.errorMessage.observe(viewLifecycleOwner){ Snackbar.make(requireView(),getString(R.string.error_fetch_post, it),Snackbar.LENGTH_SHORT).show() }


        advertiseView(this@HomeFragment) // 광고 배너
    }

    private fun initView() {
        val homeList = listOf<Home>(
            Home(HomeType.RANK ,viewModel.firstRankList.value, null)
        )
        val homeAdapter = HomeAdapter(
            homeList = homeList,
            onClickRankCard = { onClickRankCard(it, TAG) },
            onClickRankDetail = {
                showHorizontalFragment(
                    R.id.fv_main, requireActivity(),
                    RankDetailFragment(viewModel, onClickRankCard = { onClickRankCard(it, RankDetailFragment.TAG) }, onClickBackBtn = onClickBackBtn),
                    RankDetailFragment.TAG)
                onClickDetail("이달의 레시피 랭킹")
            }
        )
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvHome.adapter = homeAdapter
    }

    private fun advertiseView(fragment: Fragment){ //광고배너 화면
        val advertiseAdapter = AdvertiseVpAdapter(fragment)
        advertiseAdapter.addFragment(AdvertiseFirstFragment())
        advertiseAdapter.addFragment(AdvertiseSecondFragment())
        advertiseAdapter.addFragment(AdvertiseThirdFragment(onClickDetail, onClickBackBtn))
        advertiseAdapter.addFragment(AdvertiseFourthFragment())

        binding.vpAd.adapter = advertiseAdapter
        binding.vpAd.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val child = binding.vpAd.getChildAt(0)
        (child as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER

        binding.homeIndicator.setViewPager(binding.vpAd)

        binding.vpAd.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position
            }
        })

        // 자동 슬라이딩 시작
        startAutoSlide()
    }

    // 자동 슬라이드 시작
    private fun startAutoSlide() {
        val swipeTimer = Timer()
        val handler = Handler(Looper.getMainLooper())
        val update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            binding.vpAd.setCurrentItem(currentPage++, true)
        }

        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)
    }

    private fun onClickRankCard(postId: Int, currentScreen: String){
        showVerticalFragment(R.id.fv_main,
            requireActivity(),
            PostFragment(onClickBackBtn, postId, onShowBottomBar, currentScreen, postDeleteCallBack = {viewModel.fetchRank(0)}),
            PostFragment.TAG
        )
        onClickDetail("이달의 레시피 랭킹")
        onHideBottomBar()
    }

}