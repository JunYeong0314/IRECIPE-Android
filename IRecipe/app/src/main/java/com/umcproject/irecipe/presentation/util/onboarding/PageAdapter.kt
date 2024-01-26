package com.example.myapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umcproject.irecipe.presentation.util.onboarding.OnboardingViewModel

class PageAdapter (
    private val fragmentActivity: FragmentActivity,
    private val fragments:List<Fragment>,
    private val viewModel: OnboardingViewModel
):FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return  when(position){
            0-> return OnboardingOneFragment()
            1-> return OnboardingTwoFragment()
            else -> return OnboardingThreeFragment(viewModel = viewModel)
        }
    }
}