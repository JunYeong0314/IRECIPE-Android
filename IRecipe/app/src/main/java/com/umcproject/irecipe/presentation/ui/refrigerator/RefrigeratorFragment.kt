package com.umcproject.irecipe.presentation.ui.refrigerator

import android.view.LayoutInflater
import android.view.ViewGroup
import com.umcproject.irecipe.databinding.FragmentRefrigeratorBinding
import com.umcproject.irecipe.presentation.util.BaseFragment

class RefrigeratorFragment: BaseFragment<FragmentRefrigeratorBinding>() {
    companion object{
        const val TAG = "RefrigeratorFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRefrigeratorBinding {
        return FragmentRefrigeratorBinding.inflate(inflater, container, false)
    }
}