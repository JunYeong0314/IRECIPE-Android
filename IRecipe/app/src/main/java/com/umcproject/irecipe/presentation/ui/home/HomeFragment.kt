package com.umcproject.irecipe.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.umcproject.irecipe.databinding.FragmentHomeBinding
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseFirstFragment
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseFourthFragment
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseSecondFragment
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseThirdFragment
import com.umcproject.irecipe.presentation.ui.home.advertise.AdvertiseVpAdapter
import com.umcproject.irecipe.presentation.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment(
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit
): BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by viewModels()
//    private var homeDatas = ListOf(
        // 이달의 레시피 랭킹    이따 어케묶지 있는지 물어보자 아니 근데
        // 나의 냉장고 유통기한 )
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
//        initView()
        advertiseView()
    }

    private fun initView() {

        val homeAdapter = HomeAdapter(
//            homeDatas
//            onClickDetail()
//            onClickItem = { showVerticalFragment(R.id.fv_main, requireActivity(),) } 포스트 호출 후 구현
        )
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvHome.adapter = homeAdapter
    }

    private fun advertiseView(){
        val advertiseAdapter = AdvertiseVpAdapter(this)
        advertiseAdapter.addFragment(AdvertiseFirstFragment())
        advertiseAdapter.addFragment(AdvertiseSecondFragment())
        advertiseAdapter.addFragment(AdvertiseThirdFragment())
        advertiseAdapter.addFragment(AdvertiseFourthFragment())

        binding.vpAd.adapter = advertiseAdapter
        binding.vpAd.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

}