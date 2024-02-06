package com.umcproject.irecipe.presentation.ui.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentModalBottomSheetMyBinding


class ModalBottomSheetMyFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentModalBottomSheetMyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModalBottomSheetMyBinding.inflate(inflater,container,false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.llPostDelete.setOnClickListener {
            val modal = ModalBottomSheetDeleteFragment()
            modal.show(childFragmentManager, ModalBottomSheetDeleteFragment.TAG)
        }
//        binding.llPostModify.setOnClickListener {  } 피그마 구현 후 | 바로 편집하기인지?
    }

    companion object {
        const val TAG = "BasicBottomModalSheet"
    }
}