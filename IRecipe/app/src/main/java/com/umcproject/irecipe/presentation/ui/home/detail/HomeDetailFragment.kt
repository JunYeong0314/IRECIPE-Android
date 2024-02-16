package com.umcproject.irecipe.presentation.ui.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.umcproject.irecipe.databinding.FragmentHomeDetailBinding
import com.umcproject.irecipe.presentation.util.BaseFragment

class HomeDetailFragment(
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit,
    private val onHideTitle: () -> Unit
) : BaseFragment<FragmentHomeDetailBinding>() {

    companion object{
        const val TAG = "HomeDetailFragment"
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeDetailBinding {
        return FragmentHomeDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onDestroy() {
        super.onDestroy()
        onHideTitle()
    }
    private fun initView(){
        binding.rvHomeDetail.layoutManager= GridLayoutManager(requireActivity(), 2)
//        binding.rvHomeDetail.adapter = HomeDetailAdapter()
    }

}