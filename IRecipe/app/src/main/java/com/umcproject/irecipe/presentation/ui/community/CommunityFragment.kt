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
import com.umcproject.irecipe.presentation.ui.community.post.WritePostFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity
import com.umcproject.irecipe.presentation.util.Util.showVerticalFragment

class CommunityFragment(
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit
): BaseFragment<FragmentCommunityBinding>() {

    private var postDatas = ArrayList<Post>()
    lateinit var postAdapter: CommunityPostAdapter

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
        onClickPost() // 글쓰기 버튼 이벤트
    }

    private fun initView() {
        postAdapter = CommunityPostAdapter(postDatas)
        binding.rvPost.adapter = postAdapter
        binding.rvPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapterClick(postAdapter)

    }

    private fun onClickPost(){
        binding.btnMakePost.setOnClickListener {
            showVerticalFragment(R.id.fv_main,requireActivity(),WritePostFragment(onClickBackBtn),WritePostFragment.TAG)
            onClickDetail("커뮤니티 글쓰기")
        }
    }

    private fun adapterClick(postAdapter: CommunityPostAdapter) {
        postAdapter.setMyItemClickListener(object : CommunityPostAdapter.MyItemClickListener {
            override fun onItemClick(post: Post, position:Int) {
                val bundle = Bundle()
                val gson = Gson()
                val postJson = gson.toJson(post)
                bundle.putString("post", postJson)

                val postFragment = PostFragment(onClickBackBtn, position,
                    CommunityFragment(onClickDetail,onClickBackBtn)
                )
                postFragment.arguments = bundle

                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fv_main, postFragment)
                    .addToBackStack(null)
                    .commit()
                //showFragment(R.id.fv_main,requireActivity(),PostFragment(onClickBackBtn),PostFragment.TAG)
                onClickDetail("커뮤니티")
            }

//            override fun onItemDelete(position: Int) {
//                postAdapter.removeItem(position)
//            }
        })
    }

    fun deletePost(index: Int) { // 수정 필요
        postAdapter.removeItem(index)
    }

}