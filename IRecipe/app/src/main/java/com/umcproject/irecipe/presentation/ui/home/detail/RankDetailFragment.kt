package com.umcproject.irecipe.presentation.ui.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentRankDetailBinding
import com.umcproject.irecipe.domain.model.PostRank
import com.umcproject.irecipe.presentation.ui.home.HomeViewModel
import com.umcproject.irecipe.presentation.ui.home.RankAdapter
import com.umcproject.irecipe.presentation.util.BaseFragment


class RankDetailFragment(
    private val viewModel: HomeViewModel,
    private val onClickRankCard: (Int) -> Unit,
    private val onClickBackBtn: (String) -> Unit
) : BaseFragment<FragmentRankDetailBinding>() {
    private var selectBtn: String = "ALL"
    private var rankPage = 0
    private var scrollPosition = 0

    init { viewModel.fetchRank(0) }

    companion object{
        const val TAG = "RankDetailFragment"
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRankDetailBinding {
        return FragmentRankDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAll.isSelected = true

        viewModel.rankState.observe(viewLifecycleOwner) {
            if (it == 200) initView()
            else Snackbar.make(requireView(),getString(R.string.error_server_fetch_post, it), Snackbar.LENGTH_SHORT).show()
        }
        viewModel.errorMessage.observe(viewLifecycleOwner){ Snackbar.make(requireView(),getString(R.string.error_fetch_post, it), Snackbar.LENGTH_SHORT).show() }
        viewModel.isEmptyList.observe(viewLifecycleOwner){ binding.llEmpty.visibility = View.VISIBLE }

        scrollListener() // 스크롤 감지 함수 (페이징 감지)
        onClickBtn() // 카테고리 버튼 이벤트
    }

    private fun initView() {
        binding.llEmpty.visibility = View.GONE

        binding.rvRankDetail.apply {
            adapter = RankAdapter(viewModel.getPostRank(), onClickRankCard = onClickRankCard, true, requireContext())
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            scrollToPosition(scrollPosition)
        }
    }

    private fun onClickBtn(){
        val buttons = listOf(
            binding.btnAll, binding.btnKorean, binding.btnChinese,
            binding.btnJapanese,binding.btnWestern,binding.btnUnusual,
            binding.btnSimple,binding.btnHigh
        )
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                buttons.forEach { it.isSelected = false }
                it.isSelected = true

                selectBtn = when(index) {
                    0 -> "ALL"
                    1 -> "KOREAN"
                    2 -> "CHINESE"
                    3 -> "JAPANESE"
                    4 -> "WESTERN"
                    5 -> "UNIQUE"
                    6 -> "SIMPLE"
                    7 -> "ADVANCED"
                    else -> ""
                }

                rankPage = 0
                viewModel.fetchRankCategory(rankPage, selectBtn)
            }
        }
    }

    private fun scrollListener(){
        binding.rvRankDetail.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if(lastVisibleItemPosition == totalItemCount-1){
                    rankPage++
                    scrollPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                    viewModel.fetchRankCategory(rankPage, selectBtn)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        onClickBackBtn("")
    }

}