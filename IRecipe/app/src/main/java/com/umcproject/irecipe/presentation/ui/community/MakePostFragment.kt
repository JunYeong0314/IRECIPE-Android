package com.umcproject.irecipe.presentation.ui.community

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentMakePostBinding
import com.umcproject.irecipe.presentation.util.BaseFragment

class MakePostFragment : BaseFragment<FragmentMakePostBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMakePostBinding {
        return FragmentMakePostBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPrevPage.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnPost.setOnClickListener {
            requireActivity().supportFragmentManager
                .popBackStack()

            requireActivity().supportFragmentManager
                .beginTransaction().replace(R.id.fv_main,PostFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        binding.btnFoodType.setOnClickListener {
            val modal = ModalBottomSheetFoodType()
            modal.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
            modal.show(childFragmentManager, ModalBottomSheetFoodType.TAG)
        }

        binding.btnLevel.setOnClickListener {
            val modal = ModalBottomSheetLevel()
            modal.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
            modal.show(childFragmentManager, ModalBottomSheetLevel.TAG)
        }

        wordsLimit(binding.tvTitle, binding.tvTitleCnt, 20)
        wordsLimit(binding.tvSubtitle, binding.tvSubtitleCnt, 50)
    }
    fun wordsLimit(editText: EditText, cntView : TextView, limit: Int) {
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
    fun updateLevelButtonText(text: String) {
        val button = binding.btnLevel
        button.text = text
        val params = button.layoutParams
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT
        button.layoutParams = params
    }
    fun updateTypeButtonText(text: String) {
        binding.btnFoodType.text = text
    }
}