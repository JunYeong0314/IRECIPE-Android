package com.example.myapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter (private val fragmentActivity: FragmentActivity, private val fragments:List<Fragment>):FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return  when(position){
            0-> return OnboardingOneFragment()
            1-> return OnboardingTwoFragment()
            else -> return OnboardingThreeFragment()
        }
    }
}