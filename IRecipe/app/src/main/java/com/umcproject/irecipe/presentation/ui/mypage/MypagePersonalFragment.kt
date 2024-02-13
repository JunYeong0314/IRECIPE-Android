package com.umcproject.irecipe.presentation.ui.mypage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.marginStart
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentSignupFirstBinding
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.presentation.ui.signup.step.SecondStepFragment
import com.umcproject.irecipe.presentation.ui.signup.step.dialog.AllergyChoiceDialog
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity
import com.umcproject.irecipe.presentation.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.threadName

@AndroidEntryPoint
class MypagePersonalFragment(
    private val onCLickBackBtn: (String) -> Unit
) : BaseFragment<FragmentSignupFirstBinding>() {
    private val viewModel: MypageViewModel by viewModels()
    companion object{
        const val TAG = "MypagePersonalFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignupFirstBinding {
        return FragmentSignupFirstBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //화면 이름 변경
        val mainActivity = activity as? MainActivity
        mainActivity?.binding?.tvTitle?.text = "개인정보"

        observeName()
        setGender()
        binding.clAge.setOnClickListener{
            setAge(it)
        }
        observeNick()
        onClickAllergy()

        viewModel.isComplete.observe(requireActivity(), Observer { complete->
            modifyBtnActive(complete)
        })

        if(mainActivity?.binding?.tvTitle?.text.toString() == "개인정보"){
            changeMargin(binding.layoutName)
            changeMargin(binding.layoutNick)
            changeMargin(binding.layoutGender)
            changeMargin(binding.layoutAge)
            changeMargin(binding.layoutAllergy)

            changeTextView(binding.tvName, 16f)
            changeTextView(binding.tvNick, 16f)
            changeTextView(binding.tvGender, 16f)
            changeTextView(binding.tvAge, 16f)
            changeTextView(binding.tvAllergy, 16f)
            change()
        }
    }
    override fun onDestroy() {
        val mainActivity = activity as? MainActivity
        super.onDestroy()
        onCLickBackBtn("마이페이지")
        mainActivity?.binding?.btmMain?.visibility = View.VISIBLE
    }
    private fun changeMargin(layout: View) {
        val layoutParams = layout.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginStart = 50
        layoutParams.marginEnd = 50
        layout.layoutParams = layoutParams
    }

    private fun changeTextView(textView: TextView, textSizeInSp: Float) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeInSp)
    }

    fun change(){
        val param = binding.signupLayout.layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = 30
        binding.signupLayout.layoutParams = param

        binding.layoutNick.visibility = View.VISIBLE
        binding.layoutAllergy.visibility = View.VISIBLE


        binding.tvNext.text = "수정하기"
        val params = binding.tvNext.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = 235
        binding.tvNext.layoutParams = params
    }

    private fun nameCheckActive(name: String){
        if(name != ""){
            binding.ibtnCheckName.setImageResource(R.drawable.ic_check_true)
            //viewModel.setName(name = name)
        }else{
            binding.ibtnCheckName.setImageResource(R.drawable.ic_check)
            //viewModel.setName(name = name)
        }
    }
    private fun observeName(){
        //기존 이름 정보 출력
        viewModel.nameResponse.observe(viewLifecycleOwner) { name ->
            binding.etName.setText(name)
            nameCheckActive(name)
        }
        viewModel.resultName()

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
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

    private fun nickCheck(complete: Boolean){
        if(complete){
            binding.ibtnCheckNick.setImageResource(R.drawable.ic_check_true)
            binding.tvCheckNick.visibility = View.VISIBLE
            binding.tvCheckNick.text = getString(R.string.sign_nick_complete)
            binding.tvCheckNick.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_main))
        }else{
            binding.ibtnCheckNick.setImageResource(R.drawable.ic_check)
            binding.tvCheckNick.visibility = View.VISIBLE
            binding.tvCheckNick.text = getString(R.string.sign_nick_overlap)
            binding.tvCheckNick.setTextColor(ContextCompat.getColor(requireContext(), R.color.red_1))
        }
    }

    private fun observeNick(){
        viewModel.nicknameResponse.observe(viewLifecycleOwner) { nickname ->
            binding.etNick.setText(nickname)
            nickCheck(true)
            viewModel.setNickComplete(true)
        }
        viewModel.resultNick()

        binding.etNick.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(nick: Editable?) {
                if(nick.isNullOrEmpty()){
                    binding.ibtnCheckNick.setImageResource(R.drawable.ic_check)
                    binding.tvCheckNick.text = "중복 확인해 주세요"
                    binding.tvCheckNick.setTextColor(ContextCompat.getColor(requireContext(), R.color.red_1))
                    viewModel.setNickComplete(false)
                    binding.tvCheck.isEnabled = false
                }else{
                    binding.tvCheck.isEnabled = true
                    viewModel.setNickComplete(true)
                    onClickCheckNick(nick = binding.etNick.text.toString())
                }
            }

        })
    }
    private fun onClickCheckNick(nick: String){
        binding.tvCheck.setOnClickListener {
            if(nick != ""){
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.setNick(nick).collect{ state->
                        when(state){
                            is State.Loading -> {}
                            is State.Success -> {
                                if (state.data == 200) {
                                    nickCheck(true)
                                } else {
                                    nickCheck(false)
                                }
                            }
                            is State.Error-> { nickCheck(false) }
                            is State.ServerError -> {
                                Snackbar.make(requireView(), "Server Error: ${state.code}", Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
    private fun setGender(){
        //기본 정보 출력
        viewModel.genderResponse.observe(viewLifecycleOwner) { gender ->
            if(gender.toString() == "MALE"){
                binding.rbtnMan.isChecked = true
                genderCheckActive(genderCode = 1)
            }else{
                binding.rbtnWoman.isChecked = true
                genderCheckActive(genderCode = 2)
            }
        }
        viewModel.resultGender()

        with(binding){
            rbtnMan.setOnClickListener {
                genderCheckActive(genderCode = 1)
            }
            rbtnWoman.setOnClickListener {
                genderCheckActive(genderCode = 2)
            }
        }
    }

    private fun genderCheckActive(genderCode: Int){
        if(genderCode != -1){
            binding.ibtnCheckGender.setImageResource(R.drawable.ic_check_true)
            //viewModel.setGender(genderCode = genderCode)
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
        //viewModel.setAge(getString(id))
    }


    private fun onClickAllergy(){
        var allergyList: List<String> = emptyList()
        var allergyText = ""

        binding.tvSearch.setOnClickListener {
            val dialog = AllergyChoiceDialog(
                onClickAllergyList = {
                    //viewModel.setAllergy(it)
                    allergyList = it
                },
                onClickOk = {
                    allergyList.forEach { allergy-> allergyText += "$allergy " }
                    if(allergyList.isEmpty()) binding.tvSearch.text = getString(R.string.sign_choice)
                    else binding.tvSearch.text = allergyText
                })
            dialog.show(parentFragmentManager, "AllergyDialog")
        }
    }

    private fun modifyBtnActive(isActive: Boolean){
        if(isActive){
            binding.tvNext.setBackgroundResource(R.drawable.bg_button_main)
            binding.tvNext.isEnabled = true
        }else{
            binding.tvNext.setBackgroundResource(R.drawable.bg_button_dark_gray)
            binding.tvNext.isEnabled = false
        }
    }

    private fun nextStepBtn(){
        binding.tvNext.setOnClickListener {
            Snackbar.make(requireView(), "수정하였습니다.", Snackbar.LENGTH_SHORT).show()
            //viewModel. ...수정 서버
        }
    }

}