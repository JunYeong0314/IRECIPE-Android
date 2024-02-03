package com.umcproject.irecipe.presentation.ui.mypage

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentMypageAlarmBinding
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity

class MypageAlarmFragment(
    private val onCLickBackBtn: (String) -> Unit
): BaseFragment<FragmentMypageAlarmBinding>() {

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

        setSwitchChangeListener(binding.switchMain, "switchMain_state")
        setSwitchChangeListener(binding.switchEvent, "switchEvent_state")
        setSwitchChangeListener(binding.switchActivity, "switchActivity_state")

        binding.switchMain.isChecked = getSwitchState(requireContext(), "switchMain_state")
        binding.switchEvent.isChecked = getSwitchState(requireContext(), "switchEvent_state")
        binding.switchActivity.isChecked = getSwitchState(requireContext(), "switchActivity_state")
    }
    override fun onDestroy() {
        super.onDestroy()
        onCLickBackBtn("마이페이지")
        (context as MainActivity).binding.btmMain.visibility = View.VISIBLE
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

    private fun setSwitchChangeListener(switch: SwitchMaterial, stateKey: String) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            val color = if (isChecked) ContextCompat.getColor(requireContext(), R.color.color_main)
            else ContextCompat.getColor(requireContext(), R.color.gray_1)

            switch.trackTintList = ColorStateList.valueOf(color)
            saveSwitchState(requireContext(), stateKey, isChecked)
        }
    }
}