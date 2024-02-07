package com.umcproject.irecipe.presentation.ui.refrigerator.add

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentRefrigeratorAddBinding
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util
import com.umcproject.irecipe.presentation.util.Util.popFragment

class RefrigeratorAddFragment: BaseFragment<FragmentRefrigeratorAddBinding>() {
    private val viewModel: RefrigeratorAddViewModel by viewModels()

    companion object{
        const val TAG = "RefrigeratorAddFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRefrigeratorAddBinding {
        return FragmentRefrigeratorAddBinding.inflate(inflater, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setOnClickListener { Util.touchHideKeyboard(requireActivity()) }

        // 완료버튼 observe
        viewModel.isComplete.observe(requireActivity(), Observer {
            if(it){
                binding.tvComplete.setBackgroundResource(R.drawable.bg_button_rounded)
                binding.tvComplete.isEnabled = true
            }else{
                binding.tvComplete.setBackgroundResource(R.drawable.bg_button_rounded_lightgray)
                binding.tvComplete.isEnabled = false
            }
        })

        setFoodName() // 음식 이름 설정
        setExpiration() // 음식 유통기한 설정
        binding.tvType.setOnClickListener { setFoodType(it) } // 음식 종류 설정
        binding.tvSaveInfo.setOnClickListener { setFoodSaveInfo(it) } // 음식 보관 설정
        setMemo() // 메모 설정
        onComplete() // 완료버튼 동작
    }

    private fun setFoodName(){
        binding.etName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(name: Editable?) {
                if(name.isNullOrEmpty()){
                    viewModel.setName("")
                }else{
                    viewModel.setName(binding.etName.text.toString())
                }
            }
        })
    }

    private fun setExpiration() {
        binding.tvExpiration.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), { _, selectYear, selectMonth, selectDay ->
                val formattedDate = getString(R.string.ref_date_format, selectYear, selectMonth + 1, selectDay)
                binding.tvExpiration.text = formattedDate
                viewModel.setExpiration(formattedDate)
            }, year, month, day)

            // 현재 날짜 이전을 선택 못하도록 설정
            datePickerDialog.datePicker.minDate = calendar.timeInMillis

            datePickerDialog.show()
        }
    }

    private fun setFoodType(view: View){
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.menu_food_type, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item->
            when(item.itemId){
                R.id.menu_type_meet -> { setFoodTypeText(getString(R.string.food_type_meet)) }
                R.id.menu_type_vegetable -> { setFoodTypeText(getString(R.string.food_type_vegetable)) }
                R.id.menu_type_fruit -> { setFoodTypeText(getString(R.string.food_type_fruit)) }
                R.id.menu_type_seafood -> { setFoodTypeText(getString(R.string.food_type_seafood)) }
                R.id.menu_type_drink -> { setFoodTypeText(getString(R.string.food_type_drink)) }
                R.id.menu_type_condiment -> { setFoodTypeText(getString(R.string.food_type_condiment)) }
                R.id.menu_type_dairy-> { setFoodTypeText(getString(R.string.food_type_dairy)) }
            }
            true
        }
        popupMenu.show()
    }

    private fun setFoodTypeText(type: String){
        binding.tvType.text = type
        viewModel.setType(type = type)
    }

    private fun setFoodSaveInfo(view: View){
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.menu_food_save, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item->
            when(item.itemId){
                R.id.menu_save_normal -> { setFoodSaveInfoText(getString(R.string.food_save_normal)) }
                R.id.menu_save_frozen -> { setFoodSaveInfoText(getString(R.string.food_save_frozen)) }
                R.id.menu_save_cold -> { setFoodSaveInfoText(getString(R.string.food_save_cold)) }
            }
            true
        }
        popupMenu.show()
    }

    private fun setFoodSaveInfoText(saveInfo: String){
        binding.tvSaveInfo.text = saveInfo
        viewModel.setSaveInfo(saveInfo = saveInfo)
    }

    private fun setMemo(){
        binding.etMemo.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(name: Editable?) {
                if(name.isNullOrEmpty()){
                    viewModel.setMemo("")
                }else{
                    viewModel.setMemo(binding.etMemo.text.toString())
                }
            }
        })

    }

    private fun onComplete(){
        binding.tvComplete.setOnClickListener { popFragment(requireActivity()) }
    }

}