package com.umcproject.irecipe.presentation.ui.refrigerator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentRefrigeratorBinding
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.domain.model.Refrigerator
import com.umcproject.irecipe.domain.model.mockData
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util.showAnimatedFragment

class RefrigeratorFragment(
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit
): BaseFragment<FragmentRefrigeratorBinding>() {
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
        val titleAdapter = RefrigeratorTitleAdapter(refList = mockData, onClickDetail = { goDetailPage(it) })
        binding.rvTitle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTitle.adapter = titleAdapter
    }

    private fun goDetailPage(ref: Refrigerator){
        onClickDetail(ref.title)
        showAnimatedFragment(R.id.fv_main, requireActivity(), RefrigeratorDetailFragment(ref.ingredient, onClickBackBtn), RefrigeratorDetailFragment.TAG)
    }
}
