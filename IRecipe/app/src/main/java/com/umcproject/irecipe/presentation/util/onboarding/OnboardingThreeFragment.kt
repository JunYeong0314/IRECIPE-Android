package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umcproject.irecipe.databinding.FragmentOnboardingThreeBinding
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity
import com.umcproject.irecipe.presentation.util.onboarding.OnboardingViewModel


class OnboardingThreeFragment(
    private val viewModel: OnboardingViewModel
)  : BaseFragment<FragmentOnboardingThreeBinding>() {
    // Binding 객체 선언
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?): FragmentOnboardingThreeBinding
    {
        return FragmentOnboardingThreeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        // 버튼에 클릭 리스너를 설정
        binding.button.setOnClickListener {
        // 클릭 시 MainActivity로 이동
            viewModel.setStart(true)
        }
    }

}