package com.umcproject.irecipe.presentation.ui.refrigerator.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentRefrigeratorDetailBinding
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.presentation.ui.refrigerator.detail.IngredientDetailFragment
import com.umcproject.irecipe.presentation.ui.refrigerator.detail.RefrigeratorDetailAdapter
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util

class RefrigeratorSearchFragment(
    private val ingredientList: List<Ingredient>,
    private val onClickBackBtn: (String) -> Unit
): BaseFragment<FragmentRefrigeratorDetailBinding>()  {

    companion object{
        const val TAG = "RefrigeratorSearchFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRefrigeratorDetailBinding {
        return FragmentRefrigeratorDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        onClickBackBtn(getString(R.string.bottom_refrigerator))
    }

    private fun initView(){
        binding.rvIngredientDetail.layoutManager = GridLayoutManager(requireActivity(), 4)
        binding.rvIngredientDetail.adapter = RefrigeratorDetailAdapter(
            ingredientList,
            onClickIngredient = {
                Util.showVerticalFragment(
                    R.id.fv_main,
                    requireActivity(),
                    IngredientDetailFragment(it, onClickBackBtn, TAG, workCallBack = {}),
                    IngredientDetailFragment.TAG
                )
            }
        )
    }
}