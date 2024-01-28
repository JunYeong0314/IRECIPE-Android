package com.umcproject.irecipe.presentation.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.R
import com.umcproject.irecipe.domain.model.Chat

class ChatAdapter(private val chatList: List<Chat>): RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //채팅 화면
        var leftChatView: LinearLayout = itemView.findViewById(R.id.view_left_chat_final)
        var rightChatView: LinearLayout = itemView.findViewById(R.id.view_right_chat)
        //채팅 텍스트
        var leftTextView: TextView = itemView.findViewById(R.id.tv_left_chat)
        var rightTextView: TextView = itemView.findViewById(R.id.tv_right_chat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val chatView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return MyViewHolder(chatView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val chat = chatList[position]
        if (chat.sendId == Chat.SENT_BY_ME) {
            holder.leftChatView.visibility = View.GONE
            holder.rightChatView.visibility = View.VISIBLE
            holder.rightTextView.text = chat.chat
        } else {
            holder.rightChatView.visibility = View.GONE
            holder.leftChatView.visibility = View.VISIBLE
            holder.leftTextView.text = chat.chat
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}