package com.umcproject.irecipe.presentation.ui.signup

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umcproject.irecipe.data.remote.request.login.SignUpRequest
import com.umcproject.irecipe.data.remote.service.login.NickDuplicationService
import com.umcproject.irecipe.data.remote.service.login.SignUpService
import com.umcproject.irecipe.domain.State
import com.umcproject.irecipe.domain.model.User
import com.umcproject.irecipe.domain.repository.UserDataRepository
import com.umcproject.irecipe.presentation.util.UriUtil.toFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val signUpService: SignUpService,
    private val duplicationService: NickDuplicationService,
): ViewModel() {
    private val _userInfo = MutableStateFlow(User())
    val userInfo: StateFlow<User>
        get() = _userInfo.asStateFlow()

    private val _isFirstComplete = MutableLiveData(false)
    val isFirstComplete: LiveData<Boolean>
        get() = _isFirstComplete

    private val _isSecondComplete = MutableLiveData(false)
    val isSecondComplete: LiveData<Boolean>
        get() = _isSecondComplete

    private val _isLastComplete = MutableLiveData(false)
    val isLastComplete: LiveData<Boolean>
        get() = _isLastComplete


    fun setInit(){
        viewModelScope.launch {
            _userInfo.update {
                it.copy(num = userDataRepository.getUserData().num)
            }
        }
    }

    fun setName(name: String){
        _userInfo.update { it ->
            it.copy(name = name)
        }
        checkFirstStep()
    }

    fun setGender(genderCode: Int){
        _userInfo.update { it->
            it.copy(genderCode = genderCode)
        }
        checkFirstStep()
    }

    fun setAge(age: String){
        _userInfo.update { it->
            it.copy(age = age)
        }
        checkFirstStep()
    }

    fun setPhotoUri(uri: Uri?){
        _userInfo.update {
            it.copy(photoUri = uri)
        }
    }

    fun setNick(nick: String): Flow<State<Int>> = flow {
        emit(State.Loading)

        val response = duplicationService.getNickDuplication(nickname = nick)

        if(response.isSuccessful){
            _userInfo.update { it.copy(nick = nick) }
            _isSecondComplete.value = true
            emit(State.Success(200))
        }else{
            _isSecondComplete.value = false
            emit(State.Success(400))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    fun setAllergy(allergy: List<String>){
        _userInfo.update { it->
            it.copy(allergy = mapperToAllergy(allergy))
        }
    }

    fun setLastComplete(context: Context): Flow<State<Int>> = flow {
        emit(State.Loading)

        var imagePart: MultipartBody.Part? = null
        val request = SignUpRequest(
            age = mapperToAge(_userInfo.value.age),
            allergyList = emptyList(),
            gender = _userInfo.value.genderCode,
            name = _userInfo.value.name,
            nickname = _userInfo.value.nick,
            personalId = _userInfo.value.num
        )

        _userInfo.value.photoUri?.let { uri->
            val file = toFile(context, uri)
            val image = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            imagePart = MultipartBody.Part.createFormData(name = "file", file.name, image)
        }
        request.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val response = signUpService.signUp(request, imagePart)
        val statusCode = response.code()

        response.body()?.isSuccess?.let {
            if(it){
                _isLastComplete.value = true
                userDataRepository.setUserData("access", response.body()?.result?.accessToken ?: "")
                userDataRepository.setUserData("refresh", response.body()?.result?.refreshToken ?: "")
                emit(State.Success(statusCode))
            }else{
                _isSecondComplete.value = false
                emit(State.ServerError(statusCode))
            }
        }

    }.catch { e->
        emit(State.Error(e))
    }

    private fun checkFirstStep(){
        _isFirstComplete.value = _userInfo.value.name != "" && _userInfo.value.genderCode != -1 && _userInfo.value.age != ""
    }

    fun setSecondStepComplete(check: Boolean){
        _isSecondComplete.value = check
    }

    private fun mapperToAge(age: String): Int?{
        return when(age){
            "10대" -> 1
            "20대" -> 2
            "30대" -> 3
            "40대" -> 4
            "50대" -> 5
            "60대" -> 6
            else -> null
        }
    }

    private fun mapperToAllergy(allergyList: List<String>): List<Int>{
        val mapperToAllergyList = mutableListOf<Int>()

        allergyList.forEach { it->
            when(it){
                "난류" -> { mapperToAllergyList.add(1) }
                "우유" -> { mapperToAllergyList.add(2) }
                "땅콩" -> { mapperToAllergyList.add(3) }
                "견과류" -> { mapperToAllergyList.add(4) }
                "밀" -> { mapperToAllergyList.add(5) }
                "참깨" -> { mapperToAllergyList.add(6) }
                "콩(대두)" -> { mapperToAllergyList.add(7) }
                "과일 및 채소" -> { mapperToAllergyList.add(8) }
                "해산물 및 조개류" -> { mapperToAllergyList.add(9) }
            }
        }

        return mapperToAllergyList
    }

}