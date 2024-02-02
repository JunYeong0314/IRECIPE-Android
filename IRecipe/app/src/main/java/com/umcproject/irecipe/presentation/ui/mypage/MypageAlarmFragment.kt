package com.umcproject.irecipe.presentation.ui.mypage

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentMypageAlarmBinding
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity

class MypageAlarmFragment(): BaseFragment<FragmentMypageAlarmBinding>() {

    companion object{
        const val TAG = "MypageAlarmFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMypageAlarmBinding {
        return FragmentMypageAlarmBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //화면 이름 변경
        (context as MainActivity).binding.tvTitle.text = "알림 설정"
        super.onViewCreated(view, savedInstanceState)

        binding.switchMain.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { // 스위치가 켜진 상태일 때
                binding.switchMain.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(context as MainActivity, R.color.color_main))
            } else { // 스위치가 꺼진 상태일 때
                binding.switchMain.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(context as MainActivity, R.color.gray_1))
            }
            saveSwitchState(requireContext(), "switchMain_state", isChecked)
        }
        binding.switchEvent.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { // 스위치가 켜진 상태일 때
                binding.switchEvent.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(context as MainActivity, R.color.color_main))
            } else { // 스위치가 꺼진 상태일 때
                binding.switchEvent.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(context as MainActivity, R.color.gray_1))
            }
            saveSwitchState(requireContext(), "switchEvent_state", isChecked)
        }
        binding.switchActivity.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { // 스위치가 켜진 상태일 때
                binding.switchActivity.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(context as MainActivity, R.color.color_main))
            } else { // 스위치가 꺼진 상태일 때
                binding.switchActivity.trackTintList = ColorStateList.valueOf(ContextCompat.getColor(context as MainActivity, R.color.gray_1))
            }
            saveSwitchState(requireContext(), "switchActivity_state", isChecked)
        }

        binding.switchMain.isChecked = getSwitchState(requireContext(), "switchMain_state")
        binding.switchEvent.isChecked = getSwitchState(requireContext(), "switchEvent_state")
        binding.switchActivity.isChecked = getSwitchState(requireContext(), "switchActivity_state")
    }

    fun saveSwitchState(context: Context, key: String, value: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getSwitchState(context: Context, key: String): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(key, false) // 기본값은 false
    }
}