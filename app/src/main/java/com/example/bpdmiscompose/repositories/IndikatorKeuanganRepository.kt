package com.example.bpdmiscompose.repositories

import com.example.bpdmiscompose.dataClass.IndikatorDataClass
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface IndikatorKeuanganRepository {
    val currentUser : FirebaseUser?
    suspend fun hasUser(): Boolean
    suspend fun getUserId():String
    suspend fun getYearList(): Flow<Resources<List<Int>>>
    suspend fun getIndikatorKeuangan(): Flow<Resources<List<IndikatorDataClass>>>
    suspend fun getIndikatorByParams(jenisKinerja : String, jenisKantor : String, tahun : Int, bulan : Int) : Flow<Resources<List<IndikatorDataClass>>>
    suspend fun addIndikatorKeuangan(kantor : String, jenisKantor: String, jenisKinerja: String,tahun : Int, bulan : Int, nominal : Double, onComplete: (Boolean) -> Unit)
    suspend fun updateIndikatorKeuangan(indikatorID : String, kantor: String, jenisKantor: String, jenisKinerja: String, tahun: Int, bulan: Int, nominal: Double, onResult : (Boolean) -> Unit = {})
    suspend fun deleteIndikatorKeuangan(indikatorID: String, onComplete : (Boolean) -> Unit = {})


}


