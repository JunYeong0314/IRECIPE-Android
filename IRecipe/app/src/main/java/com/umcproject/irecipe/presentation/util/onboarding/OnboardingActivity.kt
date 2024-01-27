package com.umcproject.irecipe.presentation.util.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myapplication.OnboardingOneFragment
import com.example.myapplication.OnboardingThreeFragment
import com.example.myapplication.OnboardingTwoFragment
import com.example.myapplication.PageAdapter
import com.umcproject.irecipe.databinding.ActivityOnboardingBinding
import com.umcproject.irecipe.presentation.util.BaseActivity
import com.umcproject.irecipe.presentation.util.MainActivity

class OnboardingActivity: BaseActivity<ActivityOnboardingBinding>({ ActivityOnboardingBinding.inflate(it)})
{
    private val viewModel: OnboardingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val fragment1 = OnboardingOneFragment()
        val fragment2 = OnboardingTwoFragment()
        val fragment3 = OnboardingThreeFragment(viewModel = viewModel)
        val fragments = ArrayList<Fragment>()

        viewModel.isStart.observe(this, Observer {
            if(it){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        fragments.add(fragment1)
        fragments.add(fragment2)
        fragments.add(fragment3)

        val adapter = PageAdapter(this, fragments, viewModel)

        binding.viewPager2.adapter = adapter
        binding.dotsIndicator.setViewPager(binding.viewPager2)
    }
}