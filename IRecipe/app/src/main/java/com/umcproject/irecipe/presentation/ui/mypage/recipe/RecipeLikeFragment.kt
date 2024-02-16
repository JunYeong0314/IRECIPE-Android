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
import com.umcproject.irecipe.databinding.FragmentMypageLikeBinding
import com.umcproject.irecipe.presentation.util.BaseFragment

class RecipeLikeFragment: BaseFragment<FragmentMypageLikeBinding>() {
    private val viewModel: RecipeViewModel by viewModels()
    companion object{
        const val TAG = "RecipeLikeFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMypageLikeBinding {
        return FragmentMypageLikeBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.myLikeState.observe(viewLifecycleOwner){
//            if(it == 200) initView()
//            else Snackbar.make(requireView(), getString(R.string.error_server_fetch_post, it), Snackbar.LENGTH_SHORT).show()
//        }

//        viewModel.myLikeError.observe(viewLifecycleOwner){
//            Snackbar.make(requireView(), getString(R.string.error_fetch_post, it), Snackbar.LENGTH_SHORT).show()
//        }
    }

    private fun initView() {
        binding.llRecipeLike.visibility = View.GONE
        binding.rvRecipeLike.visibility = View.VISIBLE
        val myLikeList = viewModel.getMyLikeList()
        val myLikeAdapter = RecipeLikeAdapter(myLikeList)
        binding.rvRecipeLike.adapter = myLikeAdapter
        binding.rvRecipeLike.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}