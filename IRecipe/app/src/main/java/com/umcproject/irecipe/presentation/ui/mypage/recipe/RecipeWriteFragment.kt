package com.umcproject.irecipe.presentation.ui.mypage.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umcproject.irecipe.databinding.FragmentMypageWirteBinding
import com.umcproject.irecipe.presentation.util.BaseFragment

class RecipeWriteFragment: BaseFragment<FragmentMypageWirteBinding>() {
    companion object{
        const val TAG = "RecipeWriteFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMypageWirteBinding {
        return FragmentMypageWirteBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}