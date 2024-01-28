package com.umcproject.irecipe.presentation.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import com.umcproject.irecipe.databinding.FragmentMypageBinding
import com.umcproject.irecipe.presentation.util.BaseFragment

class MypageFragment: BaseFragment<FragmentMypageBinding>() {
    companion object{
        const val TAG = "MypageFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMypageBinding {
        return FragmentMypageBinding.inflate(inflater, container, false)
    }
}