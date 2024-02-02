package com.umcproject.irecipe.presentation.ui.community.comment.qa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umcproject.irecipe.databinding.FrgamentCommentQaBinding
import com.umcproject.irecipe.domain.model.mockQAList
import com.umcproject.irecipe.presentation.util.BaseFragment

class QAFragment: BaseFragment<FrgamentCommentQaBinding>() {
    companion object{
        const val TAG = "QAFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FrgamentCommentQaBinding {
        return FrgamentCommentQaBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView(){
        val qaAdapter = QAAdapter(mockQAList)
        binding.rvQa.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvQa.adapter = qaAdapter
    }
}