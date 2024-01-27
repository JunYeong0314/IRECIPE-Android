package com.umcproject.irecipe.presentation.ui.signup.step

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentSignupFirstBinding
import com.umcproject.irecipe.presentation.ui.signup.SignUpViewModel
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util.showFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FirstStepFragment(
    private val viewModel: SignUpViewModel
): BaseFragment<FragmentSignupFirstBinding>() {

    companion object {
        const val TAG = "FirstStepFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignupFirstBinding {
        return FragmentSignupFirstBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etName.setText(viewModel.userInfo.value.name)

        // 입력항목 검사 Observe
        viewModel.isFirstComplete.observe(requireActivity(), Observer { complete->
            nextStepBtnActive(complete)
        })

        // 기존에 입력한 정보 검사
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.userInfo.collectLatest {
                nameCheckActive(name = it.name)
                genderCheckActive(gender = it.gender)
                ageCheckActive(age = it.age)
            }
        }

        // 나이 설정
        binding.clAge.setOnClickListener{
            setAge(it)
        }

        nextStepBtn() // 다음 단계 버튼 이벤트
        observeName() // 이름 작성 observe
        setGender() // 성별 설정
    }

    private fun observeName(){
        binding.etName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(name: Editable?) {
                if(name.isNullOrEmpty()){
                    nameCheckActive("") // 이름 작성 여부
                }else{
                    nameCheckActive(binding.etName.text.toString())
                }
            }

        })
    }

    private fun setGender(){
        with(binding){
            rbtnMan.setOnClickListener {
                genderCheckActive(gender = getString(R.string.sign_man))
            }
            rbtnWoman.setOnClickListener {
                genderCheckActive(gender = getString(R.string.sign_woman))
            }
        }
    }

    private fun setAge(view: View){
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.menu_age, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item->
            when(item.itemId) {
                R.id.menu_age_10 -> { setAgeText(R.string.age_10) }
                R.id.menu_age_20 -> { setAgeText(R.string.age_20) }
                R.id.menu_age_30 -> { setAgeText(R.string.age_30) }
                R.id.menu_age_40 -> { setAgeText(R.string.age_40) }
                R.id.menu_age_50 -> { setAgeText(R.string.age_50) }
                R.id.menu_age_60 -> { setAgeText(R.string.age_60) }
            }
            true
        }
        popupMenu.show()
    }

    private fun setAgeText(@StringRes id: Int){
        with(binding){
            tvChoice.text = getString(id)
            tvChoice.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            ibtnCheckAge.setImageResource(R.drawable.ic_check_true)
        }
        viewModel.setAge(getString(id))
    }

    private fun nextStepBtnActive(isActive: Boolean){
        if(isActive){
            binding.tvNext.setBackgroundResource(R.drawable.bg_button_main)
            binding.tvNext.isEnabled = true
        }else{
            binding.tvNext.setBackgroundResource(R.drawable.bg_button_dark_gray)
            binding.tvNext.isEnabled = false
        }
    }

    private fun nameCheckActive(name: String){
        if(name != ""){
            binding.ibtnCheckName.setImageResource(R.drawable.ic_check_true)
            viewModel.setName(name = name)
        }else{
            binding.ibtnCheckName.setImageResource(R.drawable.ic_check)
            viewModel.setName(name = name)
        }
    }

    private fun genderCheckActive(gender: String){
        if(gender != ""){
            binding.ibtnCheckGender.setImageResource(R.drawable.ic_check_true)
            viewModel.setGender(gender = gender)
        }
    }

    private fun ageCheckActive(age: String){
        if(age != ""){
            with(binding){
                tvChoice.text = age
                tvChoice.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                ibtnCheckAge.setImageResource(R.drawable.ic_check_true)
            }
        }
    }

    private fun nextStepBtn(){
        binding.tvNext.setOnClickListener {
            showFragment(requireActivity(), SecondStepFragment(viewModel), SecondStepFragment.TAG)
        }
    }

}