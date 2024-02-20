package com.umcproject.irecipe.presentation.util.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.umcproject.irecipe.presentation.ui.community.CommunityFragment
import com.umcproject.irecipe.presentation.ui.home.HomeFragment
import com.umcproject.irecipe.presentation.ui.mypage.MypageFragment
import com.umcproject.irecipe.presentation.ui.refrigerator.RefrigeratorFragment

class MainFragmentFactory(private val onClickDetail: (String) -> Unit,
                          private val onClickBackBtn: (String) -> Unit,
                          private val onHideBottomBar: () -> Unit,
                          private val onShowBottomBar: () -> Unit) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            HomeFragment::class.java -> HomeFragment(onClickDetail, onClickBackBtn, onHideBottomBar, onShowBottomBar)
            RefrigeratorFragment::class.java -> RefrigeratorFragment(onClickDetail, onClickBackBtn)
            CommunityFragment::class.java -> CommunityFragment(onClickDetail, onClickBackBtn, onHideBottomBar, onShowBottomBar)
            MypageFragment::class.java -> MypageFragment(onClickDetail, onClickBackBtn, onHideBottomBar, onShowBottomBar)
            else -> super.instantiate(classLoader, className)
        }
    }
}