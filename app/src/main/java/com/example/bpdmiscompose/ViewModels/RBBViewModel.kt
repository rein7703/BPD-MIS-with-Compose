package com.example.bpdmiscompose.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bpdmiscompose.dataClass.RBBIndikatorDataClass
import com.example.bpdmiscompose.repositories.RBBRepository
import com.example.bpdmiscompose.repositories.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RBBViewModel @Inject constructor (
    private  val repository: RBBRepository
): ViewModel() {
    var rbbUiState by mutableStateOf(RBBUiState())
    val currentUser = repository.currentUser
    var hasUser by mutableStateOf(false)
    var userId by mutableStateOf("")

    init{
        viewModelScope.launch {
            hasUser = repository.hasUser()
            if(hasUser){
                userId = repository.getUserId()
                getRBB()

            }
        }
    }

    fun getRBB() = viewModelScope.launch {
        repository.getRBB().collect() { it ->
            rbbUiState = rbbUiState.copy(rbbList = it)
        }
    }



    fun getRBBByMetrics(tahun : Int, jenisKinerja: String) = viewModelScope.launch {
        repository.getRBBByMetrics(tahun, jenisKinerja).collect() { it ->
            rbbUiState = rbbUiState.copy(rbbChosen = it)
        }
    }

    fun addRBBByYear(kantor : String,  jenisKinerja: String, tahun : Int, nominal: Double, onComplete : (Boolean) -> Unit = {}) = viewModelScope.launch {
        repository.addRBB(kantor = kantor,  jenisKinerja = jenisKinerja, tahun = tahun, nominal = nominal, onComplete)
    }
    fun updateRBB(rbbId : String, kantor : String,  jenisKinerja: String, tahun : Int, nominal: Double, onComplete : (Boolean) -> Unit = {}) = viewModelScope.launch {
        try{
            repository.updateRBB(RBBId = rbbId, kantor = kantor,  jenisKinerja=jenisKinerja, tahun = tahun, nominal = nominal, onComplete)
        }catch (e: Exception){
            onComplete(false)
            Log.e("RBBViewModel", "updateRBB: ${e.message}")
        }
    }
    fun deleteRBB(RBBId : String, onComplete : (Boolean) -> Unit = {}) = viewModelScope.launch {
        try {
            repository.deleteRBB(RBBId, onComplete)
        } catch (e: Exception){
            onComplete(false)
            Log.e("RBBViewModel", "deleteRBB: ${e.message}, RBBId: $RBBId")
        }
    }

    fun setRBBIdChosen(rbbId : String) = viewModelScope.launch {
        rbbUiState = rbbUiState.copy(rbbIdChosen = rbbId)
    }
    fun setRBBChosen(rbb : RBBIndikatorDataClass)= viewModelScope.launch {
        rbbUiState = rbbUiState.copy(rbbChosen = Resources.Success(data = rbb))
    }
    fun setKantorChosen(kantor : String) = viewModelScope.launch {
        rbbUiState = rbbUiState.copy(kantorChosen = kantor)
    }
    fun setTahunChosen(tahun : Int) = viewModelScope.launch {
        rbbUiState = rbbUiState.copy(tahunChosen = tahun)
    }
    fun setJenisKinerjaChosen(jenisKinerja : String) = viewModelScope.launch {
        rbbUiState = rbbUiState.copy(jenisKinerjaChosen = jenisKinerja)
    }
    fun setNominalChosen(nominal : Double) = viewModelScope.launch {
        rbbUiState = rbbUiState.copy(nominalChosen = nominal)
    }


}

data class RBBUiState(
    val rbbList: Resources<List<RBBIndikatorDataClass>> = Resources.Loading(),
    val rbbChosen : Resources<RBBIndikatorDataClass> = Resources.Loading(),
    val rbbIdChosen : String = "",
    val kantorChosen : String = "",
    val tahunChosen : Int = 0,
    val jenisKinerjaChosen : String = "",
    val nominalChosen : Double = 0.0
    )