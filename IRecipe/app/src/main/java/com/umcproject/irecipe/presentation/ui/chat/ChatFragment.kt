package com.umcproject.irecipe.presentation.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import com.umcproject.irecipe.databinding.FragmentChatBotBinding
import com.umcproject.irecipe.presentation.util.BaseFragment

class ChatFragment: BaseFragment<FragmentChatBotBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChatBotBinding {
        return FragmentChatBotBinding.inflate(inflater, container, false)
    }
}