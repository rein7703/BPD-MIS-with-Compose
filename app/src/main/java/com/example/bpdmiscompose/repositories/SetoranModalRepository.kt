package com.example.bpdmiscompose.repositories

import com.example.bpdmiscompose.dataClass.SetoranModal
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface SetoranModalRepository {
    val currentUser : FirebaseUser?
    suspend fun hasUser(): Boolean
    suspend fun getUserId():String
    suspend fun getAllSetoranModal(): Flow<Resources<List<SetoranModal>>>
    suspend fun getSingleSetoran(setoranId:String): Flow<Resources<SetoranModal>>
    suspend fun getSingleSetoranByYearAndPemda(pemdaId: String, tahun: Int): Flow<Resources<SetoranModal>>
    suspend fun getSetoranByTahun(tahun:Int): Flow<Resources<List<SetoranModal>>>
    suspend fun addSetoran(
        userId : String,
        pemdaId : String,
        tahun: Int,
        modalDisetorRUPS:Long ,
        komposisiRUPS: Int ,
        realisasiDanaSetoranModal: Long ,
        totalModalDesember: Long ,
        timestamp: Timestamp,
        onComplete : (Boolean) -> Unit
    )
    suspend fun updateSetoran(
                              setoranId:String,
                              pemdaId : String,
                              tahun: Int,
                              modalDisetorRUPS:Long ,
                              komposisiRUPS: Int ,
                              realisasiDanaSetoranModal: Long ,
                              totalModalDesember: Long ,
                              timestamp: Timestamp,
                              onResult : (Boolean) -> Unit = {}
    )
    fun deleteSetoran(setoranId: String, onComplete:(Boolean) -> Unit)
    suspend fun getAllYears() : Flow<Resources<List<Int>>>
    suspend fun getPemdaByYear(tahun : Int) : Flow<Resources<List<String>>>
    suspend fun getAllYearsByPemda(pemdaId : String) : Flow<Resources<List<Int>>>
}