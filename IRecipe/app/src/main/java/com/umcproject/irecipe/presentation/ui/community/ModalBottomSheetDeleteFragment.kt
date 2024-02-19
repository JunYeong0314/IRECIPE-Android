package com.umcproject.irecipe.presentation.ui.community

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentModalBottomSheetDeleteBinding
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.presentation.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModalBottomSheetDeleteFragment(
    private val postId: Int,
    private val onShowBottomBar: () -> Unit,
    private val postDeleteCallBack: () -> Unit
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentModalBottomSheetDeleteBinding
    private val viewModel: CommunityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentModalBottomSheetDeleteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnDelete.setOnClickListener {
            viewModel.postDeleteState.observe(viewLifecycleOwner){
                if(it == 200){
                    Snackbar.make(requireView(), "삭제에 성공하였습니다!", Snackbar.LENGTH_SHORT).show()
                    postDeleteCallBack()
                    Util.popFragment(requireActivity())
                }else{
                    Snackbar.make(requireView(), "게시글을 찾지 못했습니다.", Snackbar.LENGTH_SHORT).show()
                }
            }

            viewModel.deletePost(postId)
        }
    }

    companion object {
        const val TAG = "BasicBottomModalSheetDelete"
    }

    override fun onDestroy() {
        super.onDestroy()
        onShowBottomBar()
    }
}