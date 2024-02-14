package com.umcproject.irecipe.presentation.ui.community.post.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umcproject.irecipe.databinding.FragmentModalBottomSheetLevelBinding

class ModalBottomSheetLevelFragment (
    private val onClickLevel: (String) -> Unit
): BottomSheetDialogFragment() {
    private lateinit var binding: FragmentModalBottomSheetLevelBinding
    private var selectLevel: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentModalBottomSheetLevelBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnLevelHigh.isSelected = true
        selectLevel = "상"

        onClickLevels()
        onClickComplete()
    }

    private fun onClickLevels(){
        val buttons = listOf(binding.btnLevelHigh, binding.btnLevelMiddle, binding.btnLevelLow)
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

    private fun onClickComplete(){
        binding.btnComplete.setOnClickListener {
            onClickLevel(selectLevel)
            dismiss()
        }
    }

    companion object {
        const val TAG = "BasicBottomModalSheetLevel"
    }
}