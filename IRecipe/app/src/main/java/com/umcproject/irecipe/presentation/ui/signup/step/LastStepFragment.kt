package com.umcproject.irecipe.presentation.ui.signup.step

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umcproject.irecipe.databinding.FragmentSignupLastBinding
import com.umcproject.irecipe.presentation.ui.signup.SignUpViewModel
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity
import com.umcproject.irecipe.presentation.util.Util
import com.umcproject.irecipe.presentation.util.onboarding.OnboardingActivity

class LastStepFragment(
    private val viewModel: SignUpViewModel
): BaseFragment<FragmentSignupLastBinding>() {

    companion object {
        const val TAG = "LastStepFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignupLastBinding {
        return FragmentSignupLastBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onComplete() // 완료버튼 이벤트
        previousBtn() // 이전버튼 이벤트
    }

    private fun onComplete(){
        binding.tvNext.setOnClickListener {
            //viewModel.setLastComplete(true)
            val intent = Intent(activity, OnboardingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun previousBtn(){
        binding.tvPrevious.setOnClickListener {
            Util.popFragment(requireActivity())
        }
    }
}