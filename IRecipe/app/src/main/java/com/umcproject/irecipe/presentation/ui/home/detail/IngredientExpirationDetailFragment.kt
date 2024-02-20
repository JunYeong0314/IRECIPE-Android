package com.umcproject.irecipe.presentation.ui.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.umcproject.irecipe.databinding.FragmentExpirationIngredientDetailBinding
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.presentation.ui.home.ExpirationAdapter
import com.umcproject.irecipe.presentation.util.BaseFragment

class IngredientExpirationDetailFragment(
    private val ingredientList: List<Ingredient>,
    private val onBackBtn: (String) -> Unit,
    private val onClickIngredient: (Ingredient) -> Unit
): BaseFragment<FragmentExpirationIngredientDetailBinding>() {
    companion object{
        const val TAG = "IngredientExpirationDetailFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentExpirationIngredientDetailBinding {
        return FragmentExpirationIngredientDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView(){
        binding.rvExpiration.apply {
            adapter = ExpirationAdapter(ingredientList, onClickIngredient, true, binding.rvExpiration.context)
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackBtn("아이레시피")
    }
}