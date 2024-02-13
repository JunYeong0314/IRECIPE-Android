package com.umcproject.irecipe.presentation.ui.community.makePost

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.umcproject.irecipe.data.remote.request.NewPostRequest
import com.umcproject.irecipe.data.remote.request.PostRequestDTO
import com.umcproject.irecipe.data.remote.service.community.NewPostService
import com.umcproject.irecipe.data.remote.service.community.NewTempService
import com.umcproject.irecipe.domain.model.Post
import com.umcproject.irecipe.presentation.util.State
import com.umcproject.irecipe.presentation.util.UriUtil.toFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class NewPostViewModel @Inject constructor(
    private val newPostService: NewPostService,
    private val newTempService: NewTempService
): ViewModel() {
    private val _postInfo = MutableStateFlow(Post())

    val postInfo: StateFlow<Post>
        get() = _postInfo.asStateFlow()


    // 사용자가 입력한 데이터 추적
    fun setTitle(title: String) {
        _postInfo.update { it ->
            it.copy(title = title)
        }
    }
    fun setSubtitle(subtitle: String) {
        _postInfo.update { it ->
            it.copy(subtitle = subtitle)
        }
    }
    fun setText(text: String) {
        _postInfo.update { it ->
            it.copy(text = text)
        }
    }
    fun setPhotoUri(uri: Uri?){
        _postInfo.update {
            it.copy(postImgUri = uri)
        }
    }
    fun setLevel(level: String) {
        _postInfo.update { it ->
            it.copy(level = level)
        }
    }
    fun setCategory(category: String) {
        _postInfo.update { it ->
            it.copy(category = category)
        }
    }


//    fun sendPostToServer(context: Context): Flow<State<Int>> = flow {
//        emit(State.Loading)
//
//        var imagePart: MultipartBody.Part? = null
//        val request = NewPostRequest(
//            file = _postInfo.value.postImgUri?.toString(),
//            postRequestDTO = PostRequestDTO(
//                category = _postInfo.value.category,
//                content = _postInfo.value.text,
//                level = _postInfo.value.level,
//                status = "published", // 예시로 고정값 사용
//                subhead = _postInfo.value.subtitle,
//                title = _postInfo.value.title
//            )
//        )
//
//        _postInfo.value.postImgUri?.let { uri ->
//            val file = toFile(context, uri)
//            val image = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
//            imagePart = MultipartBody.Part.createFormData(name = "file", file.name, image)
//        }
//
//        val response = newPostService.newPostService(request, imagePart)
//        val statusCode = response.code()
//
//        response.body()?.isSuccess?.let {
//            if (it) {
//                // 서버 응답이 성공적으로 처리됐을 때
//                emit(State.Success(statusCode))
//            } else {
//                // 서버 응답이 실패했을 때
//                emit(State.ServerError(statusCode))
//            }
//        }
//    }.catch { e ->
//        emit(State.Error(e))
//    }

    fun sendPostToServer(context: Context, isTemporary: Boolean): Flow<State<Int>> = flow {
        emit(State.Loading)
        // 멀티파트파일을 서버에 전송하기 함수
        var imagePart: MultipartBody.Part? = null
        val status = if (isTemporary) "TEMP" else "POST" // 임시저장 or 등록
        val request = NewPostRequest(
            file = _postInfo.value.postImgUri?.toString(),
            postRequestDTO = PostRequestDTO(
                category = _postInfo.value.category,
                content = _postInfo.value.text,
                level = _postInfo.value.level,
                status = status,
                subhead = _postInfo.value.subtitle,
                title = _postInfo.value.title
            )
        )

        _postInfo.value.postImgUri?.let { uri ->
            val file = toFile(context, uri)
            val image = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            imagePart = MultipartBody.Part.createFormData(name = "file", file.name, image)
        }
        request.toString().toRequestBody("application/json".toMediaTypeOrNull())

        try {
            val response = newPostService.newPostService(request,imagePart)
            val statusCode = response.code()

            if (response.isSuccessful) {
                // 서버 응답 성공
                emit(State.Success(statusCode))
            } else {
                // 서버 응답 실패
                emit(State.ServerError(statusCode))
            }
        } catch (e: Exception) {
            // 네트워크 오류 등... 예외 처리
            emit(State.Error(e))
        }
    }
}