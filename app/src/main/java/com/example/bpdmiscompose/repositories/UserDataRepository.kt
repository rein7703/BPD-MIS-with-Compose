package com.example.bpdmiscompose.repositories

import com.example.bpdmiscompose.dataClass.UserDataClass
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserDataRepository{
    val currentUser: FirebaseUser?
    suspend fun getUserId():String
    suspend fun getAllUserData() : Flow<Resources<List<UserDataClass>>>
    suspend fun getUserData(email : String) : Flow<Resources<UserDataClass>>
    suspend fun addUserData(name : String, idPegawai : String, jabatan: String, nomorHP : String, email : String, status : Boolean, onComplete : (Boolean) -> Unit = {})

    suspend fun updateUserData(dataId : String, idPegawai : String, name : String, jabatan : String, email : String, nomorHP : String, status : Boolean, onComplete : (Boolean) -> Unit = {})

    suspend fun deleteUserData(dataId : String, onComplete : (Boolean) -> Unit = {})
}
