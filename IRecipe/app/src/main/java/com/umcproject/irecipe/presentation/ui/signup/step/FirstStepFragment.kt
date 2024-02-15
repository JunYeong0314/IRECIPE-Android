package com.umcproject.irecipe.presentation.ui.signup.step

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.umcproject.irecipe.presentation.util.Util.showHorizontalFragment
import com.umcproject.irecipe.presentation.util.Util.touchHideKeyboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

        binding.root.setOnClickListener { touchHideKeyboard(requireActivity()) } // 외부화면 터치시 키패드 내림

        // 입력항목 검사 Observe
        viewModel.isFirstComplete.observe(requireActivity(), Observer { complete->
            nextStepBtnActive(complete)
        })

        // 기존에 입력한 정보 검사
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.userInfo.collectLatest {
                nameCheckActive(name = it.name)
                genderCheckActive(genderCode = it.genderCode)
                ageCheckActive(age = it.age)
            }
        }

        viewModel.setInit() // 초기 설정
        binding.clAge.setOnClickListener{ setAge(it) } // 나이 설정
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
                genderCheckActive(genderCode = 1)
            }
            rbtnWoman.setOnClickListener {
                genderCheckActive(genderCode = 2)
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

    private fun genderCheckActive(genderCode: Int){
        if(genderCode != -1){
            binding.ibtnCheckGender.setImageResource(R.drawable.ic_check_true)
            viewModel.setGender(genderCode = genderCode)
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
            showHorizontalFragment(R.id.fv_signUp ,requireActivity(), SecondStepFragment(viewModel), SecondStepFragment.TAG)
            touchHideKeyboard(requireActivity())
        }
    }

}