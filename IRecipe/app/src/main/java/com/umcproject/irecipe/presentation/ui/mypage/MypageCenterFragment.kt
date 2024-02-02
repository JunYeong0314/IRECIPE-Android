package com.umcproject.irecipe.presentation.ui.mypage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentMypageCenterBinding
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.MainActivity

class MypageCenterFragment(): BaseFragment<FragmentMypageCenterBinding>() {

    companion object{
        const val TAG = "MypageCenterFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMypageCenterBinding {
        return FragmentMypageCenterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //화면 이름 변경
        (context as MainActivity).binding.tvTitle.text = "고객센터"
        wordsLimit(binding.tvCenterTitle, binding.tvCenterTitleCnt, 20)
    }

    private fun wordsLimit(editText: EditText, cntView : TextView, limit: Int) {
        editText.addTextChangedListener(object : TextWatcher { // 글자수 제한
            var maxText = ""
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                maxText = s.toString()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editText.length() > limit){
//                    Snackbar.make(requireView(), "최대 20자까지 입력 가능합니다.", Snackbar.LENGTH_SHORT).show()
                    editText.setText(maxText)
                    editText.setSelection(editText.length())
                    cntView.setText("${editText.length()} / $limit")
                } else {
                    cntView.setText("${editText.length()} / $limit")
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

}