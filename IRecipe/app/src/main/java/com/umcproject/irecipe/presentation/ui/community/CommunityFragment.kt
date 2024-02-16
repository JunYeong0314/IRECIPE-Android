package com.umcproject.irecipe.presentation.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentCommunityBinding
import com.umcproject.irecipe.presentation.ui.community.post.PostFragment
import com.umcproject.irecipe.presentation.ui.community.write.WritePostFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util.showHorizontalFragment
import com.umcproject.irecipe.presentation.util.Util.showVerticalFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment(
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit,
    private val onHideBottomBar: () -> Unit,
    private val onShowBottomBar: () -> Unit
): BaseFragment<FragmentCommunityBinding>() {
    private val viewModel: CommunityViewModel by viewModels()
    private var page = 0

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

        // 게시글 fetch
        viewModel.postState.observe(viewLifecycleOwner){
            if(it == 200) initView()
            else Snackbar.make(requireView(), getString(R.string.error_server_fetch_post, it), Snackbar.LENGTH_SHORT).show()
        }

        // 에러 감지
        viewModel.postError.observe(viewLifecycleOwner){
            Snackbar.make(requireView(), getString(R.string.error_fetch_post, it), Snackbar.LENGTH_SHORT).show()
        }

        binding.llSortBtn.setOnClickListener { onClickSort(it) } // 정렬 클릭 이벤트
        onClickPost() // 글쓰기 버튼 이벤트
    }

    private fun initView() {
        val postList = viewModel.getPostList()
        val postAdapter = CommunityPostAdapter(
            postList,
            onClickPost = { // 게시글 클릭 콜백 함수
                showHorizontalFragment(
                    R.id.fv_main, requireActivity(),
                    PostFragment(onClickBackBtn, it, viewModel, onShowBottomBar),
                    PostFragment.TAG
                )
                onHideBottomBar()
                onClickDetail("커뮤니티")
            }
        )
        binding.rvPost.adapter = postAdapter
        binding.rvPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun onClickPost(){
        binding.llWritePost.setOnClickListener {
            showVerticalFragment(
                R.id.fv_main,requireActivity(),
                WritePostFragment(
                    onClickBackBtn,
                    postCallBack = {
                        viewModel.fetchPost(0, binding.tvSort.text.toString())
                    }),
                WritePostFragment.TAG
            )
            onClickDetail("커뮤니티 글쓰기")
        }
    }

    private fun onClickSort(view: View){
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.menu_sort_post, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item->
            when(item.itemId) {
                R.id.menu_sort_basic -> {
                    binding.tvSort.text = getString(R.string.com_sort_basic)
                    viewModel.fetchPost(0, binding.tvSort.text.toString())
                }
                R.id.menu_sort_like -> {
                    binding.tvSort.text = getString(R.string.com_sort_like)
                    viewModel.fetchPost(0, binding.tvSort.text.toString())
                }
                R.id.menu_sort_score -> {
                    binding.tvSort.text = getString(R.string.com_sort_score)
                    viewModel.fetchPost(0, binding.tvSort.text.toString())
                }
            }
            true
        }
        popupMenu.show()
    }

    private fun getPostResponse(page: Int, criteria: String){

    }

}