package com.umcproject.irecipe.presentation.ui.community.makePost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umcproject.irecipe.databinding.FragmentModalBottomSheetLevelBinding

class ModalBottomSheetLevel : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentModalBottomSheetLevelBinding
    private var selectLevel: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModalBottomSheetLevelBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnHigh = binding.btnLevelHigh
        val btnMiddle = binding.btnLevelMiddle
        val btnLow = binding.btnLevelLow
        val buttons = listOf(btnHigh, btnMiddle, btnLow)

        buttons.forEach { it.isSelected = false }
        btnHigh.isSelected = true
        selectLevel = "상"

        binding.btnSelectLevel.setOnClickListener {
            (parentFragment as? MakePostFragment)?.updateLevelButtonText(selectLevel)
            dismiss()
        }


        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                buttons.forEach { it.isSelected = false }
                it.isSelected = true

                selectLevel = when(index) {
                    0 -> "상"
                    1 -> "중"
                    2 -> "하"
                    else -> ""
                }
            }
        }
    }

    companion object {
        const val TAG = "BasicBottomModalSheet"
    }
}