package com.umcproject.irecipe.presentation.ui.refrigerator.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentRefrigeratorDetailBinding
import com.umcproject.irecipe.domain.model.Ingredient
import com.umcproject.irecipe.domain.model.Refrigerator
import com.umcproject.irecipe.presentation.ui.refrigerator.RefrigeratorViewModel
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.Util
import com.umcproject.irecipe.presentation.util.Util.showVerticalFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RefrigeratorDetailFragment(
    private val refrigerator: Refrigerator,
    private val onClickBackBtn: (String) -> Unit
): BaseFragment<FragmentRefrigeratorDetailBinding>() {
    private val viewModel: RefrigeratorViewModel by viewModels()
    private var scrollPosition = 0
    private var page = 0

    companion object{
        const val TAG = "RefrigeratorDetailFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRefrigeratorDetailBinding {
        return FragmentRefrigeratorDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchType(type = refrigerator.type, page = page)

        viewModel.detailState.observe(viewLifecycleOwner){
            if(it == 200) initView()
            else Snackbar.make(requireView(), getString(R.string.error_server_refrigerator, it), Snackbar.LENGTH_SHORT).show()
        }

        scrollListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        onClickBackBtn(getString(R.string.bottom_refrigerator))
    }

    private fun initView(){
        val ingredientList =
            when(refrigerator.type){
                "AMBIENT" -> { viewModel.getNormalIngredientList() }
                "REFRIGERATED" -> { viewModel.getColdIngredientList() }
                "FROZEN" -> { viewModel.getFrozenIngredientList() }
                else -> { null }
            }
        binding.rvIngredientDetail.layoutManager = GridLayoutManager(requireActivity(), 4)
        binding.rvIngredientDetail.adapter = RefrigeratorDetailAdapter(
            ingredientList ?: emptyList(),
            onClickIngredient = {
                showVerticalFragment(
                    R.id.fv_main, requireActivity(),
                    IngredientDetailFragment(
                        it, onClickBackBtn, TAG,
                        workCallBack = {}
                    ),
                    IngredientDetailFragment.TAG)
            }
        )
    }

    private fun scrollListener(){
        binding.rvIngredientDetail.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if(lastVisibleItemPosition == totalItemCount-1){
                    page++
                    scrollPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                    viewModel.fetchType(refrigerator.type, page)
                }
            }
        })
    }
}