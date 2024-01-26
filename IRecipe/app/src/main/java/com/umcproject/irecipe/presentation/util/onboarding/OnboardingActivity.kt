package com.umcproject.irecipe.presentation.util.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.OnboardingOneFragment
import com.example.myapplication.OnboardingThreeFragment
import com.example.myapplication.OnboardingTwoFragment
import com.example.myapplication.PageAdapter
import com.umcproject.irecipe.databinding.ActivityOnboardingBinding
import com.umcproject.irecipe.presentation.util.BaseActivity

class OnboardingActivity: BaseActivity<ActivityOnboardingBinding>({ ActivityOnboardingBinding.inflate(it)})
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        var fragment1 = OnboardingOneFragment()
        var fragment2 = OnboardingTwoFragment()
        var fragment3 = OnboardingThreeFragment()
        var fragments = ArrayList<Fragment>()

        fragments.add(fragment1)
        fragments.add(fragment2)
        fragments.add(fragment3)

        var adapter = PageAdapter(this, fragments)

        binding.viewPager2.adapter = adapter
        binding.dotsIndicator.setViewPager(binding.viewPager2)
    }
}