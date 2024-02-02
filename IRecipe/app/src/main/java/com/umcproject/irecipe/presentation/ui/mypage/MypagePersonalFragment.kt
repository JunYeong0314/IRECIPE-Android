package com.umcproject.irecipe.presentation.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umcproject.irecipe.databinding.FragmentHomeBinding
import com.umcproject.irecipe.databinding.FragmentMypagePersonalBinding
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity

class MypagePersonalFragment : BaseFragment<FragmentMypagePersonalBinding>() {
    companion object{
        const val TAG = "MypagePersonalFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMypagePersonalBinding {
        return FragmentMypagePersonalBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //화면 이름 변경
        (context as MainActivity).binding.tvTitle.text = "개인정보"
    }

}