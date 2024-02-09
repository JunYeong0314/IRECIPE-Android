package com.umcproject.irecipe.presentation.ui.chat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.R
import com.umcproject.irecipe.data.remote.service.aichat.AiChatExpiryService
import com.umcproject.irecipe.data.remote.service.aichat.AiChatRandomService
import com.umcproject.irecipe.data.remote.service.aichat.AiChatRefriService
import com.umcproject.irecipe.databinding.ActivityChatBotBinding
import com.umcproject.irecipe.domain.model.Chat
import com.umcproject.irecipe.presentation.util.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.GET
import javax.inject.Inject

@AndroidEntryPoint
class ChatBotActivity: BaseActivity<ActivityChatBotBinding>({ ActivityChatBotBinding.inflate(it)}) {
    private lateinit var chatList: MutableList<Chat>
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    @Inject
    lateinit var aiChatRefriService: AiChatRefriService
    @Inject
    lateinit var aiChatExpiryService: AiChatExpiryService
    @Inject
    lateinit var aiChatRandomService: AiChatRandomService

    companion object{
        const val TAG = "ChatBotActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        chatAdapter = ChatAdapter(chatList, this)
        recyclerView.adapter = chatAdapter

        val llm = LinearLayoutManager(this)
        llm.stackFromEnd = true
        recyclerView.layoutManager = llm
    }

    private fun addToChat(message: String, sentBy: String) { //채팅 쓰는 쪽
        Handler(Looper.getMainLooper()).post {
            chatList.add(Chat(message, sentBy))
            chatAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(chatAdapter.itemCount)
        }
    }

    private fun addResponse(response: String) { //채팅 응답
        //chatList.removeAt(chatList.size - 1)
        addToChat(response, Chat.SENT_BY_BOT)
    }

    private fun resultRefri(){
        CoroutineScope(Dispatchers.IO).launch{
            val response = aiChatRefriService.aiChatRefriService()
            Log.d(TAG, response.body()?.result?.gptResponse.toString())
            addResponse(response.body()?.result?.gptResponse.toString())
        }
    }

    private fun resultRandom(){
        CoroutineScope(Dispatchers.IO).launch{
            val response = aiChatRandomService.aiChatRandom()
            Log.d(TAG, response.body()?.result?.gptResponse.toString())
            addResponse(response.body()?.result?.gptResponse.toString())
        }
    }

    private fun resultExpiry(){
        CoroutineScope(Dispatchers.IO).launch{
            val response = aiChatExpiryService.aiChatExpiryService()
            Log.d(TAG, response.body()?.result?.gptResponse.toString())
            addResponse(response.body()?.result?.gptResponse.toString())
        }
    }
    private fun onClickQuestion(){
        binding.btnChat1.setOnClickListener {
            val question = binding.btnChat1.text.toString()
            addToChat(question, Chat.SENT_BY_ME)
            resultRefri()  //냉장고 답변
        }
        binding.btnChat2.setOnClickListener {
            val question = binding.btnChat2.text.toString()
            addToChat(question, Chat.SENT_BY_ME)
            resultRandom() //랜덤 답변
        }
        binding.btnChat3.setOnClickListener {
            val question = binding.btnChat3.text.toString()
            addToChat(question, Chat.SENT_BY_ME)
            resultExpiry() //유통기한 답변
        }
        binding.btnChat4.setOnClickListener {
            val question = binding.btnChat4.text.toString()
            addToChat(question, Chat.SENT_BY_ME)
        }
    }

    private fun onClickSendMessage(){
        binding.button2.setOnClickListener {
            val question = binding.tvChat.text.toString().trim()
            addToChat(question, Chat.SENT_BY_ME)
            // editText 내용 삭제
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
