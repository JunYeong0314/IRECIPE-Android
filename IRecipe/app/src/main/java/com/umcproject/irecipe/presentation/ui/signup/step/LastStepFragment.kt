package com.umcproject.irecipe.presentation.ui.signup.step

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umcproject.irecipe.databinding.FragmentSignupLastBinding
import com.umcproject.irecipe.presentation.ui.signup.SignUpViewModel
import com.umcproject.irecipe.presentation.util.BaseFragment

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

        onComplete()
    }

    private fun onComplete(){
        binding.tvNext.setOnClickListener {
            viewModel.setLastComplete(true)
        }
    }
}