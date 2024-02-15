package com.umcproject.irecipe.presentation.ui.community.post

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umcproject.irecipe.data.remote.request.community.WritePostRequest
import com.umcproject.irecipe.data.remote.service.community.WritePostService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.WritePost
import com.umcproject.irecipe.presentation.util.UriUtil.toFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class WritePostViewModel @Inject constructor(
    private val writePostService: WritePostService
//    private val newTempService: NewTempService
): ViewModel() {
    private val _writePostInfo = MutableStateFlow(WritePost())
    val writePostInfo: StateFlow<WritePost>
        get() = _writePostInfo.asStateFlow()

    private val _isComplete = MutableLiveData<Boolean>()
    val isComplete: LiveData<Boolean>
        get() = _isComplete


    // 사용자가 입력한 데이터 추적
    fun setTitle(title: String) {
        _writePostInfo.update { it ->
            it.copy(title = title)
        }
        isComplete()
    }
    fun setSubtitle(subtitle: String) {
        _writePostInfo.update { it ->
            it.copy(subtitle = subtitle)
        }
        isComplete()
    }
    fun setContent(content: String) {
        _writePostInfo.update { it ->
            it.copy(content = content)
        }
        isComplete()
    }
    fun setPhotoUri(uri: Uri?){
        _writePostInfo.update {
            it.copy(postImgUri = uri)
        }
    }
    fun setLevel(level: String) {
        _writePostInfo.update { it ->
            it.copy(level = level)
        }
        isComplete()
    }
    fun setCategory(category: String) {
        _writePostInfo.update { it ->
            it.copy(category = category)
        }
        isComplete()
    }


    fun postToServer(context: Context, isTemporary: Boolean): Flow<State<Int>> = flow {
        emit(State.Loading)

        var imagePart: MultipartBody.Part? = null
        val status = if (isTemporary) "TEMP" else "POST" // 임시저장 or 등록

        val request = WritePostRequest(
            category = mapperToCategory(_writePostInfo.value.category),
            content = _writePostInfo.value.content,
            level = mapperToLevel(_writePostInfo.value.level),
            status = status,
            subhead = _writePostInfo.value.subtitle,
            title = _writePostInfo.value.title
        )

        _writePostInfo.value.postImgUri?.let { uri ->
            val file = toFile(context, uri)
            val image = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            imagePart = MultipartBody.Part.createFormData(name = "file", "post"+file.name, image)
        }
        request.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val response = writePostService.newPostService(request,imagePart)
        val statusCode = response.code()

        if (statusCode == 200) {
            emit(State.Success(statusCode))
        } else {
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    private fun isComplete() {
        _isComplete.value = _writePostInfo.value.level != "" && _writePostInfo.value.category != "" && _writePostInfo.value.content != "" &&
                _writePostInfo.value.subtitle != "" && _writePostInfo.value.title != ""
    }

    private fun mapperToCategory(category: String): String {
        return when (category) {
            "한식" -> "KOREAN"
            "양식" -> "WESTERN"
            "일식" -> "JAPANESE"
            "중식" -> "CHINESE"
            "이색음식" -> "UNIQUE"
            "간편요리" -> "SIMPLE"
            "고급요리" -> "ADVANCED"
            else -> "ERROR"
        }
    }
    private fun mapperToLevel(level: String): String {
        return when (level) {
            "상" -> "DIFFICULT"
            "중" -> "MID"
            "하" -> "EASY"
            else -> "ERROR"
        }
    }

}