package com.umcproject.irecipe.presentation.ui.mypage.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umcproject.irecipe.databinding.FragmentMypageLikeBinding
import com.umcproject.irecipe.presentation.util.BaseFragment

class RecipeLikeFragment: BaseFragment<FragmentMypageLikeBinding>() {
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
    }
}