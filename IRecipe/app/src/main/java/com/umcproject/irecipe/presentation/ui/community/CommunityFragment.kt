package com.umcproject.irecipe.presentation.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentCommunityBinding
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.presentation.ui.community.makePost.MakePostFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity

class CommunityFragment(
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit
): BaseFragment<FragmentCommunityBinding>() {

    private var postDatas = ArrayList<Post>()
    private lateinit var postAdapter: CommunityPostAdapter

    companion object{
        const val TAG = "CommunityFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunityBinding {
        return FragmentCommunityBinding.inflate(inflater, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.btnMakePost.setOnClickListener {// 글쓰기 상세페이지로 이동
//            showFragment(R.id.fv_main,requireActivity(),MakePostFragment(onClickBackBtn),MakePostFragment.TAG)
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fv_main, MakePostFragment(onClickBackBtn))
                .addToBackStack(null)
                .commit()
            onClickDetail("커뮤니티 글쓰기")
        }

        postAdapter = CommunityPostAdapter(postDatas)
        binding.rvPost.adapter = postAdapter
        binding.rvPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapterClick(postAdapter)
        getPost(postAdapter)

    }

    private fun adapterClick(postAdapter: CommunityPostAdapter) {
        postAdapter.setMyItemClickListener(object : CommunityPostAdapter.MyItemClickListener {
            override fun onItemClick(post: Post) {
                val bundle = Bundle()
                val gson = Gson()
                val postJson = gson.toJson(post)
                bundle.putString("post", postJson)

                val postFragment = PostFragment(onClickBackBtn)
                postFragment.arguments = bundle

                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fv_main, postFragment)
                    .addToBackStack(null)
                    .commit()
                //showFragment(R.id.fv_main,requireActivity(),PostFragment(onClickBackBtn),PostFragment.TAG)
                onClickDetail("커뮤니티")
            }
        })
    }
    private fun getPost(postAdapter: CommunityPostAdapter) { // 글쓰기 -> 데이터 얻어오기
        val postJson = arguments?.getString("post")
        postJson?.let {
            val gson = Gson()
            val post: Post = gson.fromJson(postJson, Post::class.java)
            if (!isPostContained(post)) {
                //                postAdapter.addItem(post)
                postDatas.add(0, post)
                postAdapter.notifyItemInserted(0)
            }
        }
    }

    private fun isPostContained(post: Post): Boolean {
        for (item in postDatas) {
            if (item.title == post.title && item.subtitle == post.subtitle && item.text == post.text) {
                return true
            }
        }
        return false
    }

}