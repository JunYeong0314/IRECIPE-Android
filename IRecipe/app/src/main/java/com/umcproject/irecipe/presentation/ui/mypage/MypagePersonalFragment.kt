package com.umcproject.irecipe.presentation.ui.mypage

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginStart
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentSignupFirstBinding
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity

class MypagePersonalFragment : BaseFragment<FragmentSignupFirstBinding>() {
    companion object{
        const val TAG = "MypagePersonalFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignupFirstBinding {
        return FragmentSignupFirstBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //화면 이름 변경
        (context as MainActivity).binding.tvTitle.text = "개인정보"

        if((context as MainActivity).binding.tvTitle.text.toString() == "개인정보"){
           changeMargin()
        }
    }

    fun changeMargin(){
        val param = binding.signupLayout.layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = 30
        binding.signupLayout.layoutParams = param

        binding.signupLayout.layoutParams = (binding.signupLayout.layoutParams as ViewGroup.MarginLayoutParams).apply {
            marginStart = 50
        }
        binding.signupLayout.layoutParams = (binding.signupLayout.layoutParams as ViewGroup.MarginLayoutParams).apply {
            marginEnd = 50
        }
        binding.mypageNick.visibility = View.VISIBLE
        binding.mypageAllergy.visibility = View.VISIBLE

        binding.tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        binding.tvNick.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        binding.tvGender.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        binding.tvAge.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        binding.tvAllergy.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)

        binding.tvNext.text = "수정하기"
        val params = binding.tvNext.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = 275
        binding.tvNext.layoutParams = params
    }
}