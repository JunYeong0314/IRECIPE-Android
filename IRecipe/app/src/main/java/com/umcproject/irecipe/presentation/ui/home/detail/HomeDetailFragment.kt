package com.umcproject.irecipe.presentation.ui.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentHomeDetailBinding
import com.umcproject.irecipe.domain.model.PostRank
import com.umcproject.irecipe.presentation.ui.community.CommunityViewModel
import com.umcproject.irecipe.presentation.ui.community.post.PostFragment
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util.showVerticalFragment

class HomeDetailFragment(
    private val minPostList: List<PostRank>,
    private val minPostCategoryList: List<PostRank>,
    private val onClickDetail: (String) -> Unit,
    private val onClickBackBtn: (String) -> Unit,
    private val onHideBottomBar: () -> Unit,
    private val onShowBottomBar: () -> Unit,
    private val onHideTitle: () -> Unit
) : BaseFragment<FragmentHomeDetailBinding>() {
    private val viewModel: CommunityViewModel by viewModels()
    private var selectBtn: String = ""

    companion object{
        const val TAG = "HomeDetailFragment"
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeDetailBinding {
        return FragmentHomeDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    override fun onDestroy() {
        super.onDestroy()
        onHideTitle()
    }
    private fun initView(){
        binding.btnAll.isSelected = true
        selectBtn = getString(R.string.home_detail_all)

        onClickBtns()
        binding.rvHomeDetail.layoutManager= GridLayoutManager(requireActivity(), 2)
        binding.rvHomeDetail.adapter = HomeDetailAdapter(
            minPostList,
            onClickPost = {
                showVerticalFragment(R.id.fv_main, requireActivity(),
                    PostFragment(onClickBackBtn, it, viewModel, onShowBottomBar),
                    PostFragment.TAG
                )
                onHideBottomBar()
                onClickDetail("커뮤니티")
            }
        )
    }
    private fun onClickBtns(){
        val buttons = listOf(binding.btnAll, binding.btnKorean, binding.btnChinese, binding.btnJapanese,binding.btnWestern,binding.btnUnusual,binding.btnSimple,binding.btnHigh)
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                buttons.forEach { it.isSelected = false }
                it.isSelected = true

                selectBtn = when(index) {
                    0 -> getString(R.string.home_detail_all)
                    1 -> getString(R.string.modal_korean)
                    2 -> getString(R.string.modal_chinese)
                    3 -> getString(R.string.modal_japanese)
                    4 -> getString(R.string.modal_western)
                    5 -> getString(R.string.modal_unusual)
                    6 -> getString(R.string.modal_simple)
                    7 -> getString(R.string.modal_high)
                    else -> ""
                }
            }
        }
    }

}