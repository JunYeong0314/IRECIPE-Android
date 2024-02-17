package com.umcproject.irecipe.presentation.ui.community.comment.qa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umcproject.irecipe.databinding.ItemQaBinding
import com.umcproject.irecipe.domain.model.OtherUser
import com.umcproject.irecipe.domain.model.QA
import com.umcproject.irecipe.domain.model.Writer

class QAAdapter(
    private val qaList: List<QA>
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

        /*holder.setOtherUser(qa.otherUser)
        qa.writer?.let {
            holder.setWriter(it)
        }*/
    }

    inner class ViewHolder(val binding: ItemQaBinding): RecyclerView.ViewHolder(binding.root){
        /*fun setOtherUser(otherUser: OtherUser){
            with(otherUser) {
                profile?.let { binding.ivProfile.setImageURI(it) }
                binding.tvName.text = name
                binding.tvDate.text = date.toString()
                binding.tvContent.text = content
            }
        }

        fun setWriter(writer: Writer){
            binding.flWriter.visibility = View.VISIBLE
            with(writer){
                profile?.let { binding.ivWriterProfile.setImageURI(it) }
                binding.tvWriterName.text = name
                binding.tvWriterDate.text = date.toString()
                binding.tvWriterContent.text = content
            }
        }*/
    }
}