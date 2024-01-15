package com.umcproject.irecipe.presentation.ui.community

import android.view.LayoutInflater
import android.view.ViewGroup
import com.umcproject.irecipe.databinding.FragmentCommunityBinding
import com.umcproject.irecipe.presentation.util.BaseFragment

class CommunityFragment: BaseFragment<FragmentCommunityBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunityBinding {
        return FragmentCommunityBinding.inflate(inflater, container, false)
    }
}