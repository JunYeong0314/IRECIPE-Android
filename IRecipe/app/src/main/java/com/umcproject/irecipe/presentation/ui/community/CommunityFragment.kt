package com.umcproject.irecipe.presentation.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentCommunityBinding
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity

class CommunityFragment(
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit
): BaseFragment<FragmentCommunityBinding>() {

    private var postDatas = ArrayList<Post>()
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

        postDatas.apply {
            add(Post("삼계탕 만들기","잘 먹겠습니다 반갑습니다 안녕하세요 부제목 50자 이하","텍스트 더더더 길고"))
            add(Post("젬고제목","부제목!!","본문~~!!"))
        }

        binding.btnMakePost.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fv_main, MakePostFragment(onClickBackBtn))
                .addToBackStack(null)
                .commitAllowingStateLoss()
            onClickDetail("커뮤니티 글쓰기")
        }

        val postAdapter = CommunityPostAdapter(postDatas)
        binding.rvPost.adapter = postAdapter
        binding.rvPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        postAdapter.setMyItemClickListener(object: CommunityPostAdapter.MyItemClickListener{
            override fun onItemClick(post: Post) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fv_main, PostFragment(onClickBackBtn))
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
                onClickDetail("커뮤니티")
            }


        })
    }
}