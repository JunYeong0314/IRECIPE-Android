package com.umcproject.irecipe.presentation.ui.chat

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentChatBotBinding
import com.umcproject.irecipe.domain.model.Chat
import com.umcproject.irecipe.presentation.ui.home.HomeFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity
import com.umcproject.irecipe.presentation.util.Util.popFragment
import com.umcproject.irecipe.presentation.util.Util.showAnimatedFragment
import com.umcproject.irecipe.presentation.util.Util.showFragment

class ChatFragment(): BaseFragment<FragmentChatBotBinding>() {
    companion object{
        const val TAG = "ChatFragment"
    }

    lateinit var chatList: MutableList<Chat>
    lateinit var recyclerView: RecyclerView
    lateinit var chatAdapter: ChatAdapter
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChatBotBinding {
        return FragmentChatBotBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

//        chatList = ArrayList()
//        recyclerView = binding.chatRecyclerView
//
//        val sendBtn = binding.button2
//        val editText = binding.tvChat
//
//        // setup recycler view
//        chatAdapter = ChatAdapter(chatList)
//        recyclerView.adapter = chatAdapter
//
//        val llm = LinearLayoutManager(requireContext())
//        llm.stackFromEnd = true
//        recyclerView.layoutManager = llm
//
//        //질문할 내용 입력
//        sendBtn.setOnClickListener {
//            val question = editText.text.toString().trim()
//            addToChat(question, Chat.SENT_BY_ME)
//            // editText 내용 삭제
//            editText.text.clear()
//        }
//
//        //예시버튼으로 질문하기
//        binding.btnChat1.setOnClickListener {
//            val question = binding.btnChat1.text.toString()
//            addToChat(question, Chat.SENT_BY_ME)
//        }
//        binding.btnChat2.setOnClickListener {
//            val question = binding.btnChat2.text.toString()
//            addToChat(question, Chat.SENT_BY_ME)
//        }
//        binding.btnChat3.setOnClickListener {
//            val question = binding.btnChat3.text.toString()
//            addToChat(question, Chat.SENT_BY_ME)
//        }
//        binding.btnChat4.setOnClickListener {
//            val question = binding.btnChat4.text.toString()
//            addToChat(question, Chat.SENT_BY_ME)
//        }

        //뒤로 가기
//        binding.ibtnBack.setOnClickListener{
//            /*(activity as MainActivity).binding.btmMain.visibility = View.VISIBLE //바텀바 다시 등장
//            (context as MainActivity).supportFragmentManager.beginTransaction() //homeFragment로 이동
//                .replace(R.id.fv_main, HomeFragment())
//                .commitAllowingStateLoss()*/
//            showBottomBar()
//            popFragment(requireActivity())
//        }
    }

    private fun addToChat(message: String, sentBy: String) { //채팅 쓰는 쪽
        Handler(Looper.getMainLooper()).post {
            chatList.add(Chat(message, sentBy))
            chatAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(chatAdapter.itemCount)
        }
    }

    private fun addResponse(response: String) { //채팅 응답
        chatList.removeAt(chatList.size - 1)
        addToChat(response, Chat.SENT_BY_BOT)
    }

    private fun showBottomBar(){
        (activity as MainActivity).binding.btmMain.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        showBottomBar()
    }

}