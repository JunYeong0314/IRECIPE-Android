package com.umcproject.irecipe.presentation.ui.mypage.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentMypageWirteBinding
import com.umcproject.irecipe.presentation.ui.community.CommunityPostAdapter
import com.umcproject.irecipe.presentation.ui.community.CommunityViewModel
import com.umcproject.irecipe.presentation.ui.community.post.PostFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeWriteFragment(
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit,
    private val onHideBottomBar: () -> Unit,
    private val onShowBottomBar: () -> Unit
): BaseFragment<FragmentMypageWirteBinding>() {
    private val viewModel: RecipeViewModel by viewModels()
    companion object{
        const val TAG = "RecipeWriteFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMypageWirteBinding {
        return FragmentMypageWirteBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.myWriteState.observe(viewLifecycleOwner){
            val postList = viewModel.getMyWriteList()
            //Log.d(TAG, postList.toString())
            if(it ==200 && postList.isEmpty()){
                binding.llRecipeWrite.visibility = View.GONE
                binding.rvRecipeWrite.visibility = View.VISIBLE
            }
            if(it == 200) initView()
            else Snackbar.make(requireView(), getString(R.string.error_server_fetch_post, it), Snackbar.LENGTH_SHORT).show()
        }

        viewModel.myWriteError.observe(viewLifecycleOwner){
            Log.d(TAG, "hihi")
            Snackbar.make(requireView(), getString(R.string.error_fetch_post, it), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        binding.llRecipeWrite.visibility = View.GONE
        binding.rvRecipeWrite.visibility = View.VISIBLE
        val myWriteList = viewModel.getMyWriteList()
        val myWriteAdapter = RecipeWriteAdapter(
            myWriteList,
            onClickWrite = { // 게시글 클릭 콜백 함수
                Util.showHorizontalFragment(
                    R.id.fv_main, requireActivity(),
                    PostFragment(onClickBackBtn, it, onShowBottomBar, TAG),
                    PostFragment.TAG
                )
                onHideBottomBar()
                onClickDetail("레시피 보관함")
            }
        )
        binding.rvRecipeWrite.adapter = myWriteAdapter
        binding.rvRecipeWrite.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}