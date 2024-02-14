package com.umcproject.irecipe.presentation.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentPostBinding
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.presentation.ui.community.comment.CommentFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util.showVerticalFragment


class PostFragment(private val onCLickBackBtn: (String) -> Unit,private val index: Int,private val communityFragment: CommunityFragment) : BaseFragment<FragmentPostBinding>() {
    private val gson:Gson = Gson()
    private lateinit var post: Post

    companion object{
        const val TAG = "PostFragment"
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPostBinding {
        return FragmentPostBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        onCLickBackBtn("커뮤니티")
    }

    private fun getData() {
        val postJson = arguments?.getString("post")
        if (postJson != null) {
            post = gson.fromJson(postJson, Post::class.java)
            initView(post)
        } else {
            Toast.makeText(context, "데이터를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun initView(post:Post) {
        binding.tvTitle.text = post.title
        binding.tvText.text = post.content
        binding.llBtns.setOnClickListener {
            showVerticalFragment(R.id.fv_main,requireActivity(),CommentFragment(),CommentFragment.TAG)
        }
        binding.btnPostMy.setOnClickListener {
            val modal = ModalBottomSheetMyFragment()
            modal.show(childFragmentManager, ModalBottomSheetMyFragment.TAG)
        }

    }

    fun deletePost() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        requireActivity().supportFragmentManager.popBackStack()
    }
}

