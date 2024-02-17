package com.umcproject.irecipe.presentation.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentMypageRecipeBinding
import com.umcproject.irecipe.presentation.ui.community.comment.review.ReviewFragment
import com.umcproject.irecipe.presentation.ui.mypage.recipe.RecipeLikeFragment
import com.umcproject.irecipe.presentation.ui.mypage.recipe.RecipeWriteFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity
import com.umcproject.irecipe.presentation.util.Util

class MypageRecipeFragment(
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit,
    private val onHideBottomBar: () -> Unit,
    private val onShowBottomBar: () -> Unit
): BaseFragment<FragmentMypageRecipeBinding>() {
    private var ch1:Int? = 1
    private var ch2:Int? = 0

    companion object{
        const val TAG = "MypageAlarmFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMypageRecipeBinding {
        return FragmentMypageRecipeBinding .inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        binding.btnRecipeLike.setOnClickListener{
            ch1 = 1
            ch2 = 0
            checkBtn()
            Util.showNoStackFragment(
                R.id.fv_recipe,
                requireActivity(),
                RecipeLikeFragment(),
                RecipeLikeFragment.TAG
            )
        }
        binding.btnRecipeWrite.setOnClickListener{
            ch1 = 0
            ch2 = 1
            checkBtn()
            Util.showNoStackFragment(
                R.id.fv_recipe,
                requireActivity(),
                RecipeWriteFragment(onClickDetail, onClickBackBtn, onHideBottomBar, onShowBottomBar),
                RecipeWriteFragment.TAG
            )
        }
    }

    private fun initView(){
        Util.showNoStackFragment(
            R.id.fv_recipe,
            requireActivity(),
            RecipeLikeFragment(),
            RecipeLikeFragment.TAG
        )
    }

    private fun checkBtn(){
        if(ch1 == 1){
            binding.btnRecipeLikeSelect.visibility = View.VISIBLE
            binding.btnRecipeLike.visibility = View.GONE
        }else{
            binding.btnRecipeLikeSelect.visibility = View.GONE
            binding.btnRecipeLike.visibility = View.VISIBLE
        }

        if(ch2==1){
            binding.btnRecipeWriteSelect.visibility = View.VISIBLE
            binding.btnRecipeWrite.visibility = View.GONE
        }else{
            binding.btnRecipeWriteSelect.visibility = View.GONE
            binding.btnRecipeWrite.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onClickBackBtn("마이페이지")
        (context as MainActivity).binding.btmMain.visibility = View.VISIBLE
    }

}