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
    private val onCLickBackBtn: (String) -> Unit
): BaseFragment<FragmentMypageRecipeBinding>() {

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
            Util.showNoStackFragment(
                R.id.fv_recipe,
                requireActivity(),
                RecipeLikeFragment(),
                RecipeLikeFragment.TAG
            )
        }
        binding.btnRecipeWrite.setOnClickListener{
            Util.showNoStackFragment(
                R.id.fv_recipe,
                requireActivity(),
                RecipeWriteFragment(),
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

    override fun onDestroy() {
        super.onDestroy()
        onCLickBackBtn("마이페이지")
        (context as MainActivity).binding.btmMain.visibility = View.VISIBLE
    }

}