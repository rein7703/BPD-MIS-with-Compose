package com.example.bpdmiscompose.ViewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bpdmiscompose.dataClass.UserDataClass
import com.example.bpdmiscompose.repositories.Resources
import com.example.bpdmiscompose.repositories.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor (
    private  val repository: UserDataRepository
): ViewModel() {
    var userDataUiState by mutableStateOf(UserDataUiState())

    fun getAllUserData() = viewModelScope.launch {
        repository.getAllUserData().collect() { it ->
            userDataUiState = userDataUiState.copy(userDataList = it)
            Log.i(TAG, userDataUiState.userDataList.data.toString())
        }
    }
    fun getUserData(email : String) = viewModelScope.launch {
        repository.getUserData(email = email).collect() { it ->
            userDataUiState = userDataUiState.copy(userData = it)
        }
    }



    fun addUserData(idPegawai : String, name : String, jabatan : String, email : String, nomorHp : String, status : Boolean, onComplete : (Boolean) -> Unit = {}) = viewModelScope.launch {
        repository.addUserData(idPegawai = idPegawai, name = name, jabatan = jabatan, email = email, nomorHP = nomorHp, status = status, onComplete = onComplete)
    }


}


data class UserDataUiState(
    val userDataList : Resources<List<UserDataClass>> = Resources.Loading(),
    val userData: Resources<UserDataClass> = Resources.Loading()
)