package com.example.bpdmiscompose.ViewModels

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

    private fun getRBB() = viewModelScope.launch {
        repository.getRBB().collect() { it ->
            rbbUiState = rbbUiState.copy(rbbList = it)
        }
    }

    fun getRBBByMetrics(kantor : String, tahun : Int, jenisKinerja: String) = viewModelScope.launch {
        repository.getRBBByMetrics(kantor, tahun, jenisKinerja).collect() { it ->
            rbbUiState = rbbUiState.copy(rbbChosen = it)
        }
    }

    fun addRBBByYear(kantor : String,  jenisKinerja: String, tahun : Int, nominal: Double, onComplete : (Boolean) -> Unit = {}) = viewModelScope.launch {
        repository.addRBB(kantor = kantor,  jenisKinerja = jenisKinerja, tahun = tahun, nominal = nominal, onComplete)
    }
    fun updateRBB(rbbId : String, kantor : String,  jenisKinerja: String, tahun : Int, nominal: Double, onComplete : (Boolean) -> Unit = {}) = viewModelScope.launch {
        repository.updateRBB(RBBId = rbbId, kantor = kantor,  jenisKinerja=jenisKinerja, tahun = tahun, nominal = nominal, onComplete)
    }
    fun deleteRBB(RBBId : String, onComplete : (Boolean) -> Unit = {}) = viewModelScope.launch {
        repository.deleteRBB(RBBId, onComplete)
    }



}

data class RBBUiState(
    val rbbList: Resources<List<RBBIndikatorDataClass>> = Resources.Loading(),
    val rbbChosen : Resources<RBBIndikatorDataClass> = Resources.Loading()
    )