package com.umcproject.irecipe.presentation.ui.community.comment.qa

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.databinding.ItemQaBinding
import com.umcproject.irecipe.domain.model.OtherUser
import com.umcproject.irecipe.domain.model.QA
import com.umcproject.irecipe.domain.model.Writer

class QAAdapter(
    private val qaList: List<QA>,
    private val isMyPost: Boolean,
    private val onClickReply: (String, Int) -> Unit
): RecyclerView.Adapter<QAAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = qaList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val qa = qaList[position]

        qa.writer?.let { holder.setWriter(qa.writer) }
        qa.otherUser?.let { holder.setOtherUser(qa.otherUser) }
        holder.initView(isMyPost)
        holder.onClickReply()
        holder.observeReply()
        qa.qnaId?.let { holder.onClickReplyComplete(it) }
    }

    inner class ViewHolder(val binding: ItemQaBinding): RecyclerView.ViewHolder(binding.root){

        fun initView(isMyPost: Boolean){
            if(!isMyPost) binding.btnReply.visibility = View.VISIBLE
        }
        fun setOtherUser(otherUser: List<OtherUser>){
            val other = otherUser.getOrNull(0)
            other?.let {
                binding.btnReply.visibility = View.GONE
                binding.flOther.visibility = View.VISIBLE
                binding.tvWriterName.text = other.nick
                binding.tvWriterDate.text = other.createdAt?.substring(0, 10)
                binding.tvWriterContent.text = other.content
            }
        }

        fun setWriter(writer: Writer){
            with(writer){
                binding.tvName.text = nick
                binding.tvDate.text = createdAt?.substring(0, 10)
                binding.tvContent.text = content
            }
        }

        fun onClickReply(){
            binding.btnReply.setOnClickListener {
                binding.btnReply.visibility = View.GONE
                binding.llEtReply.visibility = View.VISIBLE
            }
        }

        fun observeReply(){
            binding.etReply.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(reply: Editable?) {
                    binding.btnReplyComplete.isEnabled = !reply.isNullOrEmpty()
                }
            })
        }

        fun onClickReplyComplete(parentId: Int){
            binding.btnReplyComplete.setOnClickListener { onClickReply(binding.etReply.text.toString(), parentId) }
        }
    }
}