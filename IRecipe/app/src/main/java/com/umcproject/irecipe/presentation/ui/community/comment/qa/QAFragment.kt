package com.umcproject.irecipe.presentation.ui.community.comment.qa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentCommentQaBinding
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.presentation.ui.community.CommunityViewModel
import com.umcproject.irecipe.presentation.ui.community.comment.WriteCommentFragment
import com.umcproject.irecipe.presentation.ui.community.comment.WriteType
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class QAFragment(
    private val viewModel: CommunityViewModel,
    private val postId: Int,
    private val isMyPost: Boolean
): BaseFragment<FragmentCommentQaBinding>() {
    companion object{
        const val TAG = "QAFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommentQaBinding {
        return FragmentCommentQaBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchQA(postId)

        viewModel.qaState.observe(viewLifecycleOwner){
            if(it == 200) initView()
            else Snackbar.make(requireView(), "[Error: $it] 게시글을 불러올 수 없습니다", Snackbar.LENGTH_SHORT).show()
        }

        initView()
        onClickSetQA()
    }

    private fun initView(){
        binding.rvQa.apply {
            adapter = QAAdapter(viewModel.getQAList(), isMyPost, onClickReply = { reply, parentId -> setReply(reply, parentId) })
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        if(isMyPost) binding.llWriteQa.visibility = View.GONE
    }

    private fun setReply(reply: String, parentId: Int){
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.setReplyQA(postId, parentId, reply).collect{ state->
                when(state){
                    is State.Loading -> {}
                    is State.Success -> {
                        Snackbar.make(requireView(), "답변을 등록했어요!", Snackbar.LENGTH_SHORT).show()
                        viewModel.fetchQA(postId)
                    }
                    is State.ServerError -> {
                        Snackbar.make(requireView(), "[Error:${state.code}]답변요청이 실패했습니다", Snackbar.LENGTH_SHORT).show()
                    }
                    is State.Error -> { Snackbar.make(requireView(), state.exception.message.toString(), Snackbar.LENGTH_SHORT).show() }
                }
            }
        }
    }

    private fun onClickSetQA(){
        binding.llWriteQa.setOnClickListener {
            Util.showVerticalFragment(
                R.id.fv_main, requireActivity(),
                WriteCommentFragment(viewModel, postId, reviewCallBack = {}, qaCallBack = { viewModel.fetchQA(postId) }, WriteType.QA),
                WriteCommentFragment.TAG
            )
        }
    }
}