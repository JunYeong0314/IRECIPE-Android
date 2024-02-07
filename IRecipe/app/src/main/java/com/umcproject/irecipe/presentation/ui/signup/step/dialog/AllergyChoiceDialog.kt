package com.umcproject.irecipe.presentation.ui.signup.step.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.umcproject.irecipe.databinding.DialogAllergyBinding

class AllergyChoiceDialog(
    private val onClickAllergyList: (List<String>) -> Unit
): DialogFragment() {
    private val allergyList = mutableListOf<String>()
    private val binding: DialogAllergyBinding by lazy {
        DialogAllergyBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView() // 초기화면 설정
        onClickAllergy() // 알러지 선택 이벤트

        binding.tvCancel.setOnClickListener { dismiss() } // 취소버튼 이벤트
        binding.tvOk.setOnClickListener { // 확인버튼 이벤트
            onClickAllergyList(allergyList)
            dismiss()
        }
    }

    private fun initView(){
        val width = resources.displayMetrics.widthPixels * 0.90 // 90% of screen width
        dialog?.window?.setLayout(width.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setDimAmount(0.5F)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun onClickAllergy(){
        val checkBox = arrayOf(
            binding.cbNan, binding.cbMilk, binding.cbPeanut, binding.cbNuts, binding.cbFlour,
            binding.cbSesame, binding.cbBean, binding.cbFruit, binding.cbSea
        )

        checkBox.forEach { allergy->
            allergy.setOnClickListener {
                if(allergy.isChecked) allergyList.add(allergy.text.toString())
                else allergyList.remove(allergy.text.toString())
            }
        }
    }
}