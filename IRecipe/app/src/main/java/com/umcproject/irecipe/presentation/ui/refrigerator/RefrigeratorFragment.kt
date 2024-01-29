package com.umcproject.irecipe.presentation.ui.refrigerator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umcproject.irecipe.databinding.FragmentRefrigeratorBinding
import com.umcproject.irecipe.domain.model.mockData
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        val titleAdapter = RefrigeratorTitleAdapter(refList = mockData)
        binding.rvTitle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTitle.adapter = titleAdapter
    }
}
