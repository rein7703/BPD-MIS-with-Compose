package com.example.bpdmiscompose.repositories

import com.example.bpdmiscompose.dataClass.RBBIndikatorDataClass
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface RBBRepository{
    val currentUser : FirebaseUser?
    suspend fun hasUser(): Boolean
    suspend fun getUserId():String
    suspend fun getRBB(): Flow<Resources<List<RBBIndikatorDataClass>>>
    suspend fun getRBBByMetrics(kantor : String, tahun : Int, jenisKinerja: String): Flow<Resources<RBBIndikatorDataClass>>
    suspend fun addRBB(kantor : String, jenisKinerja: String, tahun : Int, nominal: Double, onComplete : (Boolean) -> Unit = {})
    suspend fun updateRBB(RBBId : String, kantor: String, jenisKinerja: String, tahun: Int, nominal: Double, onResult : (Boolean) -> Unit = {})
    suspend fun deleteRBB(RBBId : String, onComplete: (Boolean) -> Unit = {})
}