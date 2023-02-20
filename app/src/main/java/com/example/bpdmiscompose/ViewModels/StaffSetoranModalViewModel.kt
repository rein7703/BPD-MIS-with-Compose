package com.example.bpdmiscompose.ViewModels

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bpdmiscompose.repositories.Resources
import com.example.bpdmiscompose.repositories.SetoranModal
import com.example.bpdmiscompose.repositories.SetoranModalRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.GetTokenResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StaffSetoranModalViewModel @Inject constructor (
    private  val repository: SetoranModalRepository
): ViewModel() {
    var staffSetoranModalUiState by mutableStateOf(StaffSetoranModalUiState())
    val currentUser = repository.currentUser
    var hasUser by mutableStateOf(false)
    var userId by mutableStateOf("")

    init {
        viewModelScope.launch {
            hasUser = repository.hasUser()
            if (hasUser) {
                userId = repository.getUserId()
                getAllYears()
                getAllSetoranModal()
            } else {
                staffSetoranModalUiState = staffSetoranModalUiState.copy(setoranList = Resources.Error(throwable = Throwable(message = "User is not logged in")))
            }
        }
    }


    fun loadByYear(tahun : Int){
        if(hasUser){
            if(userId.isNotBlank()){
                getSetoranByTahun(tahun)
            }else{
                staffSetoranModalUiState = staffSetoranModalUiState.copy(setoranList = Resources.Error(throwable = Throwable(message = "User belom login")))
            }
        }
    }

    fun loadAll(){
        if(hasUser){
            if(userId.isNotBlank()){
                getAllYears()
                getAllSetoranModal()

            }else{
                staffSetoranModalUiState = staffSetoranModalUiState.copy(setoranList = Resources.Error(throwable = Throwable(message = "User belom login")))
            }
        }
    }

    fun addData(
        context : Context,
        pemdaId : String,
        tahun: Int,
        modalDisetorRUPS:Long,
        komposisiRUPS: Int,
        realisasiDanaSetoranModal: Long,
        totalModalDesember: Long,
        timestamp: Timestamp = Timestamp.now(),
        onComplete : (Boolean) -> Unit
    ){
                if(hasUser){
                    if(userId.isNotBlank()) {
                        currentUser?.getIdToken(true)?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val tokenResult: GetTokenResult? = task.result
                                if (tokenResult != null) {
                                    val customClaims: Map<String, Any>? = tokenResult.claims
                                    if (customClaims != null) {
                                        if (customClaims["isAdmin"] as Boolean) {
                                            addSetoran(
                                                context,
                                                userId,
                                                pemdaId,
                                                tahun,
                                                modalDisetorRUPS,
                                                komposisiRUPS,
                                                realisasiDanaSetoranModal,
                                                totalModalDesember,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else{
                    onComplete(false)
                }
        }


    fun deleteSetoran(context:Context ,setoranId:String) = repository.deleteSetoran(setoranId, onComplete = {
        if(it){
            Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show()
        }
    })


    private fun getPemdaByYear(tahun : Int) = viewModelScope.launch {
        repository.getPemdaByYear(tahun).collect(){
            staffSetoranModalUiState = staffSetoranModalUiState.copy(pemdaList = it)
        }
    }

    private fun getAllSetoranModal() = viewModelScope.launch {
        repository.getAllSetoranModal().collect{
            staffSetoranModalUiState = staffSetoranModalUiState.copy(setoranList = it)
        }
    }

    private fun getAllYears() = viewModelScope.launch {
        repository.getAllYears().collect{
            staffSetoranModalUiState = staffSetoranModalUiState.copy(yearList = it)
        }
    }

    private fun getSetoranByTahun(tahun : Int) = viewModelScope.launch {
        repository.getSetoranByTahun(tahun).collect{
            staffSetoranModalUiState = staffSetoranModalUiState.copy(setoranList = it)
        }
    }

    private fun addSetoran(
        context: Context,
        userId : String,
        pemdaId : String,
        tahun: Int,
        modalDisetorRUPS:Long,
        komposisiRUPS: Int,
        realisasiDanaSetoranModal: Long,
        totalModalDesember: Long,
        timestamp: Timestamp = Timestamp.now(),
        onComplete : (Boolean) -> Unit = {}
    ) = viewModelScope.launch {
        repository.getPemdaByYear(tahun).collect(){
            staffSetoranModalUiState = staffSetoranModalUiState.copy(pemdaList = it)
        }

        val pemdaList = staffSetoranModalUiState.pemdaList.data?.map{it}
        Log.i(ContentValues.TAG, "Pemda yang sudah terdaftar: $pemdaList")


        if (pemdaList != null) {
            try{
                require(!pemdaList.contains(pemdaId)){"Data ${pemdaId} untuk tahun ${tahun.toString()} sudah ada. Mohon hapus atau ubah data"}
                repository.addSetoran(
                    userId,
                    pemdaId,
                    tahun,
                    modalDisetorRUPS,
                    komposisiRUPS,
                    realisasiDanaSetoranModal,
                    totalModalDesember,
                    timestamp,
                    onComplete
                )
                Toast.makeText(context, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()

            } catch (e: Exception){
                onComplete(false)
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            repository.addSetoran(
                userId,
                pemdaId,
                tahun,
                modalDisetorRUPS,
                komposisiRUPS,
                realisasiDanaSetoranModal,
                totalModalDesember,
                timestamp,
                onComplete
            )
        }









    }

}

data class  StaffSetoranModalUiState(
    val yearList : Resources<List<Int>> = Resources.Loading(),
    val setoranList : Resources<List<SetoranModal>> = Resources.Loading(),
    val pemdaList : Resources<List<String>> = Resources.Loading()
)

