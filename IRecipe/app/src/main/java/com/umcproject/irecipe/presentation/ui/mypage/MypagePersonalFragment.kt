package com.umcproject.irecipe.presentation.ui.mypage

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginStart
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentSignupFirstBinding
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity

class MypagePersonalFragment(
    private val onCLickBackBtn: (String) -> Unit
) : BaseFragment<FragmentSignupFirstBinding>() {
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
            changeMargin(binding.layoutName)
            changeMargin(binding.layoutNick)
            changeMargin(binding.layoutGender)
            changeMargin(binding.layoutAge)
            changeMargin(binding.layoutAllergy)

            changeTextView(binding.tvName, 16f)
            changeTextView(binding.tvNick, 16f)
            changeTextView(binding.tvGender, 16f)
            changeTextView(binding.tvAge, 16f)
            changeTextView(binding.tvAllergy, 16f)
           change()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        onCLickBackBtn("마이페이지")
        (context as MainActivity).binding.btmMain.visibility = View.VISIBLE
    }
    private fun changeMargin(layout: View) {
        val layoutParams = layout.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginStart = 50
        layoutParams.marginEnd = 50
        layout.layoutParams = layoutParams
    }

    private fun changeTextView(textView: TextView, textSizeInSp: Float) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeInSp)
    }

    fun change(){
        val param = binding.signupLayout.layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = 30
        binding.signupLayout.layoutParams = param

        binding.layoutNick.visibility = View.VISIBLE
        binding.layoutAllergy.visibility = View.VISIBLE


        binding.tvNext.text = "수정하기"
        val params = binding.tvNext.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = 235
        binding.tvNext.layoutParams = params
    }
}