package com.umcproject.irecipe.presentation.ui.community.makePost

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentMakePostBinding
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.presentation.util.BaseFragment

class MakePostFragment(
    private val onCLickBackBtn: (String) -> Unit
) : BaseFragment<FragmentMakePostBinding>() {

    companion object{
        const val TAG = "MakePostFragment"
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMakePostBinding {
        return FragmentMakePostBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPost.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val subtitle = binding.etSubtitle.text.toString()
            val text = binding.etText.text.toString()

            if (title.isEmpty() || subtitle.isEmpty() || text.isEmpty()) {
                Snackbar.make(view, "모든 값을 입력해주세요", Snackbar.LENGTH_SHORT).show()
            } else {
                val post = Post(title, subtitle, text)

                val gson = Gson()
                val postJson = gson.toJson(post)
                val bundle = Bundle().apply { putString("post",postJson) }
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
                requireActivity().supportFragmentManager.popBackStack()

                requireActivity().supportFragmentManager.findFragmentByTag("CommunityFragment")?.arguments = bundle

            }

        }

        binding.btnFoodType.setOnClickListener {
            it.isSelected = true
            val modal = ModalBottomSheetFoodType()
//            modal.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
            modal.show(childFragmentManager, ModalBottomSheetFoodType.TAG)
            modal.dialog?.setOnDismissListener {
                binding.btnFoodType.isSelected = false
            }
        }

        binding.btnLevel.setOnClickListener {
            it.isSelected = true
            val modal = ModalBottomSheetLevel()
            modal.show(childFragmentManager, ModalBottomSheetLevel.TAG)
        }

        wordsLimit(binding.etTitle, binding.tvTitleCnt, 20)
        wordsLimit(binding.etSubtitle, binding.tvSubtitleCnt, 50)
    }
    override fun onDestroy() {
        super.onDestroy()
        onCLickBackBtn("커뮤니티")
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
    fun updateLevelButtonText(text: String) {
        val button = binding.btnLevel
        button.text = text
        button.isSelected = false
        val params = button.layoutParams
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT
        button.layoutParams = params
    }
    fun updateTypeButtonText(text: String) {
        val button = binding.btnFoodType
        button.text = text
        button.isSelected = false
    }
}