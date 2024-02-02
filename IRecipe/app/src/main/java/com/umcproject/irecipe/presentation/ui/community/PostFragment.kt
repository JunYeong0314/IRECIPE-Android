package com.umcproject.irecipe.presentation.ui.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentPostBinding
import com.umcproject.irecipe.presentation.util.BaseFragment


class PostFragment(private val onCLickBackBtn: (String) -> Unit) : BaseFragment<FragmentPostBinding>() {


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPostBinding {
        return FragmentPostBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        onCLickBackBtn("커뮤니티")
    }

}