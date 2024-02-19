package com.umcproject.irecipe.presentation.ui.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentModalBottomSheetMyBinding
import com.umcproject.irecipe.presentation.ui.community.post.PostFragment
import com.umcproject.irecipe.presentation.ui.home.HomeFragment
import com.umcproject.irecipe.presentation.ui.mypage.recipe.RecipeWriteFragment


class ModalBottomSheetMyFragment(
    private val postId: Int,
    private val onShowBottomBar: () -> Unit,
    private val postDeleteCallBack: () -> Unit
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentModalBottomSheetMyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModalBottomSheetMyBinding.inflate(inflater,container,false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.llPostDelete.setOnClickListener {
            val modal = ModalBottomSheetDeleteFragment(postId, onShowBottomBar, postDeleteCallBack)
            modal.show(childFragmentManager, ModalBottomSheetDeleteFragment.TAG)
        }
//        binding.llPostModify.setOnClickListener {  }
    }

    companion object {
        const val TAG = "BasicBottomModalSheetMy"
    }
}