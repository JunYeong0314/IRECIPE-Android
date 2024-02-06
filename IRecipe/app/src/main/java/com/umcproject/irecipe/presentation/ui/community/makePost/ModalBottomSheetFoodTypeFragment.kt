package com.umcproject.irecipe.presentation.ui.community.makePost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umcproject.irecipe.databinding.FragmentModalBottomSheetFoodTypeBinding

class ModalBottomSheetFoodType : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentModalBottomSheetFoodTypeBinding
    private var selectType: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModalBottomSheetFoodTypeBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttons = listOf( binding.btnKorean, binding.btnChinese, binding.btnJapanese,
            binding.btnWestern, binding.btnUnusual, binding.btnSimple, binding.btnHigh)
        buttons.forEach { it.isSelected = false }
        binding.btnKorean.isSelected = true
        selectType = "한식"

        binding.btnSelectFoodType.setOnClickListener {
            (parentFragment as? MakePostFragment)?.updateTypeButtonText(selectType)
            dismiss()
        }
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                buttons.forEach { it.isSelected = false }
                it.isSelected = true

                selectType = when(index) {
                    0 -> "한식"
                    1 -> "중식"
                    2 -> "일식"
                    3 -> "양식"
                    4 -> "이색음식"
                    5 -> "간편요리"
                    6 -> "고급요리"
                    else -> ""
                }
            }
        }
    }
    companion object {
        const val TAG = "BasicBottomModalSheet"
    }

}