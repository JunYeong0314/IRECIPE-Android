package com.umcproject.irecipe.presentation.ui.chat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.R
import com.umcproject.irecipe.data.remote.service.chat.AiChatDislikeService
import com.umcproject.irecipe.data.remote.service.chat.AiChatExpiryService
import com.umcproject.irecipe.data.remote.service.chat.AiChatRandomService
import com.umcproject.irecipe.data.remote.service.chat.AiChatRefriService
import com.umcproject.irecipe.data.remote.service.chat.AiChatService
import com.umcproject.irecipe.databinding.ActivityChatBotBinding
import com.umcproject.irecipe.databinding.ItemChatBinding
import com.umcproject.irecipe.domain.model.Chat
import com.umcproject.irecipe.presentation.util.BaseActivity
import com.umcproject.irecipe.presentation.util.Util
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatBotActivity: BaseActivity<ActivityChatBotBinding>({ ActivityChatBotBinding.inflate(it)}) {
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var chatList: MutableList<Chat>
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private var lastClickedButton: Button? = null


    @Inject
    lateinit var aiChatRefriService: AiChatRefriService
    @Inject
    lateinit var aiChatExpiryService: AiChatExpiryService
    @Inject
    lateinit var aiChatRandomService: AiChatRandomService
    @Inject
    lateinit var aiChatDislikeService: AiChatDislikeService
    @Inject
    lateinit var aiChatService: AiChatService

    companion object{
        const val TAG = "ChatBotActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.root.setOnClickListener { Util.touchHideKeyboard(this) }

        observeChange()
        chatList = ArrayList()
        initView() // 어뎁터 설정
        onClickSendMessage()//질문할 내용 입력
        onClickOftenQuestion() // 자주하는 질문 클릭 이벤트
        onClickQuestion() // 예시버튼으로 질문하기

        //뒤로가기
        binding.ibtnBack.setOnClickListener{ finish() }
    }

    private fun initView(){
        recyclerView = binding.chatRecyclerView

        // setup recycler view
        chatAdapter = ChatAdapter(chatList)
        recyclerView.adapter = chatAdapter

        val llm = LinearLayoutManager(this)
        llm.stackFromEnd = true
        recyclerView.layoutManager = llm
    }

    private fun addToChat(message: String, sentBy: String) { //채팅 쓰는 쪽
        Handler(Looper.getMainLooper()).post {
            if(chatList.isNotEmpty() && sentBy == Chat.SENT_BY_BOT) chatList.removeLast()
            chatList.add(Chat(message, sentBy))
            chatAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(chatAdapter.itemCount)
        }
    }

    private fun addResponse(response: String) { //채팅 응답
        addToChat(response, Chat.SENT_BY_BOT)
    }

    private fun observeChange(){
        viewModel.expiryResponse.observe(this@ChatBotActivity, Observer { addResponse(it) })

        viewModel.chatResponse.observe(this@ChatBotActivity, Observer { addResponse(it) })

        viewModel.refriResponse.observe(this@ChatBotActivity, Observer { addResponse(it) })

        viewModel.randomResponse.observe(this@ChatBotActivity, Observer { addResponse(it) })

        viewModel.dislikeResponse.observe(this@ChatBotActivity, Observer { addResponse(it) })

        viewModel.isLoading.observe(this@ChatBotActivity, Observer {
            if(it) addToChat("", Chat.LOADING)
        })
    }

    private fun onClickQuestion(){
        binding.btnChat1.setOnClickListener {
            val question = binding.btnChat1.text.toString()
            addToChat(question, Chat.SENT_BY_ME)
            lastClickedButton = binding.btnChat1

            viewModel.resultRefri() //냉장고 답변
        }
        binding.btnChat2.setOnClickListener {
            val question = binding.btnChat2.text.toString()
            addToChat(question, Chat.SENT_BY_ME)
            lastClickedButton = binding.btnChat2

            viewModel.resultRandom() //랜덤 답변
        }
        binding.btnChat3.setOnClickListener {
            val question = binding.btnChat3.text.toString()
            addToChat(question, Chat.SENT_BY_ME)
            lastClickedButton = binding.btnChat3

            viewModel.resultExpiry() //유통기한 답변
        }
        binding.btnChat4.setOnClickListener {
            val question = binding.btnChat4.text.toString()
            addToChat(question, Chat.SENT_BY_ME)
            lastClickedButton = binding.btnChat4
        }
    }

    private fun onClickSendMessage(){
        binding.button2.setOnClickListener {
            val question = binding.tvChat.text.toString().trim()
            addToChat(question, Chat.SENT_BY_ME)
            // editText 내용 삭제
            if (lastClickedButton == binding.btnChat4) {
                viewModel.resultDislike(question)
            }else{
                viewModel.resultChat(question)
            }
            lastClickedButton = null
            binding.tvChat.text.clear()
        }
    }

    private fun onClickOftenQuestion(){
        var chatState = ChatState.HIDDEN

        binding.btnPlus.setOnClickListener {
            when(chatState) {
                ChatState.HIDDEN -> {
                    binding.frameChat.visibility = View.VISIBLE
                    binding.btnPlus.setBackgroundResource(R.drawable.ic_minus_round)
                    chatState = ChatState.SHOWING
                }
                ChatState.SHOWING -> {
                    binding.frameChat.visibility = View.GONE
                    binding.btnPlus.setBackgroundResource(R.drawable.bg_chat_button_show)
                    chatState = ChatState.HIDDEN
                }
            }
        }

        binding.btnChatClose.setOnClickListener {
            binding.frameChat.visibility = View.GONE
            binding.btnPlus.setBackgroundResource(R.drawable.bg_chat_button_show)
            chatState = ChatState.HIDDEN
        }
    }

    enum class ChatState {
        SHOWING, HIDDEN
    }

}
