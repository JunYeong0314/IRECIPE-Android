package com.umcproject.irecipe.presentation.ui.community.makePost

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.umcproject.irecipe.R
import com.umcproject.irecipe.databinding.FragmentMakePostBinding
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.presentation.util.BaseFragment
import com.umcproject.irecipe.presentation.util.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MakePostFragment(
    private val onCLickBackBtn: (String) -> Unit
) : BaseFragment<FragmentMakePostBinding>() {
    private val viewModel: NewPostViewModel by viewModels()

    // 이미지 콜백 변수
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.ivImage.setImageURI(uri)
            viewModel.setPhotoUri(uri = uri)
            binding.cvImage.visibility = View.VISIBLE
            Log.d("IMG",": ${uri}")
        }
    }

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

        initView(view)
    }
    override fun onDestroy() {
        super.onDestroy()
        onCLickBackBtn("커뮤니티")
    }

    private fun initView(view: View) {
        onClickPhoto()

        binding.btnPost.setOnClickListener { // 서버에러 500
            val title = binding.etTitle.text.toString()
            val subtitle = binding.etSubtitle.text.toString()
            val text = binding.etText.text.toString()
            val level = binding.btnLevel.text.toString()
            val category = binding.btnFoodType.text.toString()

            if (title.isEmpty() || subtitle.isEmpty() || text.isEmpty() || level=="난이도" || category=="요리 종류") {
                Snackbar.make(view, "모든 값을 입력해주세요", Snackbar.LENGTH_SHORT).show()
            } else {
                sendToServerData(title, subtitle, text, level, category, isTemporary = false) // 데이터를 뷰모델로 전달
            }
        }

        binding.btnTemp.setOnClickListener { // 임시저장
            val title = binding.etTitle.text.toString()
            val subtitle = binding.etSubtitle.text.toString()
            val text = binding.etText.text.toString()
            val level = binding.btnLevel.text.toString()
            val category = binding.btnFoodType.text.toString()

            sendToServerData(title, subtitle, text, level, category, isTemporary = true)
        }

        binding.btnFoodType.setOnClickListener { // 카테고리 설정
            it.isSelected = true
            val modal = ModalBottomSheetFoodType()
            modal.show(childFragmentManager, ModalBottomSheetFoodType.TAG)
            modal.dialog?.setOnDismissListener {
                binding.btnFoodType.isSelected = false
            }
        }

        binding.btnLevel.setOnClickListener { // 난이도 설정
            it.isSelected = true
            val modal = ModalBottomSheetLevel()
            modal.show(childFragmentManager, ModalBottomSheetLevel.TAG)
        }

        wordsLimit(binding.etTitle, binding.tvTitleCnt, 20)
        wordsLimit(binding.etSubtitle, binding.tvSubtitleCnt, 50)


    }
    private fun onClickPhoto(){
        binding.btnAddImg.setOnClickListener {
            photoDialog()
        }
    }

    private fun photoDialog(){
        val array = arrayOf(
            getString(R.string.profile_pickAlbum),
            getString(R.string.profile_cancel)
        )

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.com_addImg))
            .setItems(array
            ) { _, which ->
                when(which){
                    0 -> { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
                    1 -> {}
                }
            }
            .show()
    }
    private fun sendToServerData(title: String, subtitle: String, text: String, level: String, category: String, isTemporary: Boolean) { // 임시저장 or 등록
        val sanitizedTitle = if (title.isEmpty()) "." else title
        val sanitizedSubtitle = if (subtitle.isEmpty()) "." else subtitle
        val sanitizedText = if (text.isEmpty()) "." else text

        viewModel.setTitle(sanitizedTitle)
        viewModel.setSubtitle(sanitizedSubtitle)
        viewModel.setText(sanitizedText)
        viewModel.setLevel(level)
        viewModel.setCategory(category)
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.sendPostToServer(requireContext(), isTemporary).collect { state ->
                // Flow는 서버로 데이터 보냄, 응답을 받아옴
                // Flow에서 발생하는 이벤트를 수신하고 처리
                when (state) {
                    is State.Loading -> {}

                    is State.Success -> {
                        requireActivity().supportFragmentManager.popBackStack()
                    }

                    is State.ServerError -> {
                        val message = if (isTemporary) "임시저장" else "저장"
                        Snackbar.make(requireView(), "[${state._data}] $message 실패", Snackbar.LENGTH_SHORT).show()
                    }

                    is State.Error -> {
                        Log.d("ERROR", state.exception.message.toString())
                        val message = if (isTemporary) "임시저장" else "저장"
                        Snackbar.make(requireView(), "[Error] $message 실패", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

//    private fun sendToServerData(title: String, subtitle: String, text: String) {
//        viewModel.setTitle(title)
//        viewModel.setSubtitle(subtitle)
//        viewModel.setText(text)
//        CoroutineScope(Dispatchers.Main).launch {
//            viewModel.sendPostToServer(requireContext()).collect { state ->
//                // Flow는 서버로 데이터 보냄, 응답을 받아옴
//                // Flow에서 발생하는 이벤트를 수신하고 처리
//                when (state) {
//                    is State.Loading -> {}
//
//                    is State.Success -> {
//                        requireActivity().supportFragmentManager.popBackStack()
//                    }
//
//                    is State.ServerError -> {
//                        Snackbar.make(
//                            requireView(),
//                            "[${state._data}] 레시피 작성 실패",
//                            Snackbar.LENGTH_SHORT
//                        ).show()
//                    }
//
//                    is State.Error -> {
//                        Log.d("ERROR", state.exception.message.toString())
//                        Snackbar.make(requireView(), "[Error] 레시피 작성 실패", Snackbar.LENGTH_SHORT)
//                            .show()
//                    }
//                }
//            }
//        }
//    }





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