package com.example.bpdmiscompose.repositories

import com.example.bpdmiscompose.dataClass.SkedulSetoranModal
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface SkedulSetoranRepository {
    val currentUser : FirebaseUser?
    suspend fun hasUser(): Boolean
    suspend fun getUserId():String
    suspend fun getSkedulSetoran(): Flow<Resources<List<SkedulSetoranModal>>>
    suspend fun getYearList(): Flow<Resources<List<Int>>>
    suspend fun getPemegangSahamList(): Flow<Resources<List<String>>>
    suspend fun getSkedulSetoranByPemegangSaham(pemegangSaham: String): Flow<Resources<List<SkedulSetoranModal>>>
    suspend fun getSkedulSetoranByTahun(tahun: Int): Flow<Resources<List<SkedulSetoranModal>>>
    suspend fun addSkedulSetoran(pemegangSaham: String, tahun: Int, nominal : Long, onComplete: (Boolean) -> Unit)
    suspend fun updateSkedulSetoran(pemegangSaham: String, tahun: Int, nominal : Long, skedulId : String, onResult : (Boolean) -> Unit = {})
    suspend fun deleteSkedulSetoran(skedulId: String, onComplete: (Boolean) -> Unit)
}