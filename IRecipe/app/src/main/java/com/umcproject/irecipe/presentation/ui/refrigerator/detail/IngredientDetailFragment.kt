package com.umcproject.irecipe.presentation.ui.refrigerator.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentIngredientDetailBinding
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.presentation.ui.community.CommunityViewModel
import com.umcproject.irecipe.presentation.ui.refrigerator.RefrigeratorFragment
import com.umcproject.irecipe.presentation.ui.refrigerator.RefrigeratorViewModel
import com.umcproject.irecipe.presentation.ui.refrigerator.Type
import com.umcproject.irecipe.presentation.ui.refrigerator.process.RefrigeratorProcessFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util
import com.umcproject.irecipe.presentation.util.Util.getEngResourceId
import com.umcproject.irecipe.presentation.util.Util.mapperToKorIngredientCategory
import com.umcproject.irecipe.presentation.util.Util.mapperToKorIngredientType
import com.umcproject.irecipe.presentation.util.Util.popFragment
import com.umcproject.irecipe.presentation.util.Util.showVerticalFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IngredientDetailFragment(
    private val ingredient: Ingredient,
    private val onClickBackBtn: (String) -> Unit,
    private val currentFragment: String,
    private val onIngredientCallBack: () -> Unit
): BaseFragment<FragmentIngredientDetailBinding>() {
    private val viewModel: RefrigeratorViewModel by viewModels()

    companion object{
        const val TAG = "IngredientFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIngredientDetailBinding {
        return FragmentIngredientDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setIngredientInfo() // 초기 정보 설정
        binding.btnConfirm.setOnClickListener {
            viewModel.deleteState.observe(viewLifecycleOwner){
            if(it == 200){
                Util.popFragment(requireActivity())
                onIngredientCallBack()
                Snackbar.make(requireView(), "삭제에 성공하였습니다!", Snackbar.LENGTH_SHORT).show()
            }else{
                Snackbar.make(requireView(), "게시글을 찾지 못했습니다.", Snackbar.LENGTH_SHORT).show()
            }
        }

            viewModel.deleteIngredient(ingredient.id)} // 확인 클릭 이벤트
        binding.btnModify.setOnClickListener {
            showVerticalFragment(
                R.id.fv_main, requireActivity(),
                RefrigeratorProcessFragment(onClickBackBtn, Type.MODIFY, ingredient, workCallBack = { viewModel.allIngredientFetch() }),
                RefrigeratorProcessFragment.TAG
            )}
    }

    private fun setIngredientInfo(){
        binding.tvName.text = ingredient.name
        binding.tvType.text = mapperToKorIngredientType(ingredient.type)
        binding.tvCategory.text = mapperToKorIngredientCategory(ingredient.category)
        binding.tvExpiration.text = ingredient.expiration
        binding.tvMemo.text = ingredient.memo

        val photo = getEngResourceId(ingredient.category)
        photo?.let { binding.civImage.setImageResource(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(currentFragment == RefrigeratorFragment.TAG) onClickBackBtn("나의 냉장고")
    }
}