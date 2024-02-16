package com.umcproject.irecipe.presentation.ui.home.advertise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umcproject.irecipe.databinding.FragmentAdvertiseFirstBinding
import com.umcproject.irecipe.databinding.FragmentAdvertiseThirdBinding
import com.umcproject.irecipe.presentation.util.BaseFragment

class AdvertiseThirdFragment(): BaseFragment<FragmentAdvertiseThirdBinding>() {

    companion object{
        const val TAG = "AdvertiseThirdFragment"
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdvertiseThirdBinding {
        return FragmentAdvertiseThirdBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}