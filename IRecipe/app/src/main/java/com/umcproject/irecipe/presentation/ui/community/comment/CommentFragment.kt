package com.umcproject.irecipe.presentation.ui.community.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentCommentBinding
import com.umcproject.irecipe.presentation.ui.community.comment.qa.QAFragment
import com.umcproject.irecipe.presentation.ui.community.comment.review.ReviewFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util.showNoStackFragment

class CommentFragment: BaseFragment<FragmentCommentBinding>() {
    companion object{
        const val TAG = "CommentFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommentBinding {
        return FragmentCommentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView() // 최초 탭 화면 설정
        onTabClick() // 탭 클릭이벤트
    }

    private fun initView(){
        showNoStackFragment(R.id.fv_comment, requireActivity(), ReviewFragment(), ReviewFragment.TAG)
    }

    private fun onTabClick(){
        binding.tlComment.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position){
                    0 -> {
                        showNoStackFragment(R.id.fv_comment, requireActivity(), ReviewFragment(), ReviewFragment.TAG)
                    }
                    1 -> {
                        showNoStackFragment(R.id.fv_comment, requireActivity(), QAFragment(), QAFragment.TAG)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}