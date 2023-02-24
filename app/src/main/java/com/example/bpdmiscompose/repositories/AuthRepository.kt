package com.example.bpdmiscompose.repositories

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun login(email: String, password : String): Resource<FirebaseUser>
    suspend fun signup(name : String, email : String, password: String,): Resource<FirebaseUser>
    fun logout()
    suspend fun addUser(email : String, password: String,emailAdmin : String,  passwordAdmin: String, onComplete: (Boolean) -> Unit = {})
    suspend fun updatePassword(email: String, oldPassword : String, newPassword : String, onComplete : (Boolean) -> Unit = {})

}