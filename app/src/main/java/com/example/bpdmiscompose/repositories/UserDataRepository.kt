package com.example.bpdmiscompose.repositories

import com.example.bpdmiscompose.dataClass.UserDataClass
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserDataRepository{
    val currentUser: FirebaseUser?
    suspend fun getUserId():String
    fun getAllUserData() : Flow<Resources<List<UserDataClass>>>
    fun getUserData(email : String) : Flow<Resources<UserDataClass>>
    fun addUserData(name : String, idPegawai : String, jabatan: String, nomorHP : String, email : String, status : Boolean, onComplete : (Boolean) -> Unit = {})
}
