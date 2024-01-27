package com.umcproject.irecipe.presentation.util.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnboardingViewModel: ViewModel() {
    private val _isStart = MutableLiveData(false)
    val isStart: LiveData<Boolean>
        get() = _isStart

    fun setStart(isStart: Boolean){
        _isStart.value = isStart
    }
}