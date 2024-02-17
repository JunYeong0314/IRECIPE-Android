package com.umcproject.irecipe.presentation.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.ItemChatBinding
import com.umcproject.irecipe.domain.model.Chat
import com.umcproject.irecipe.presentation.util.Util.touchHideKeyboard

class ChatAdapter(
    private val chatList: List<Chat>,
): RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val chat = chatList[position]

        when(chat.sendId){
            Chat.SENT_BY_ME -> { holder.chatMe(chat.chat) }
            Chat.SENT_BY_BOT -> { holder.chatBot(chat.chat) }
            Chat.LOADING -> { holder.viewLoad() }
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    inner class MyViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun chatMe(content: String?){
            binding.viewLeftChatFinal.visibility = View.GONE
            binding.viewRightChat.visibility = View.VISIBLE
            binding.tvRightChat.text = content
        }

        fun chatBot(content: String?){
            binding.viewRightChat.visibility = View.GONE
            binding.lottieDotLoading.visibility = View.GONE
            binding.viewLeftChatFinal.visibility = View.VISIBLE
            binding.tvLeftChat.text = content
        }

        fun viewLoad(){
            binding.viewLeftChat.visibility = View.VISIBLE
            binding.viewRightChat.visibility = View.GONE
            binding.lottieDotLoading.visibility = View.VISIBLE
        }
    }

}