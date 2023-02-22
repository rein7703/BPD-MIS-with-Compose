package com.example.bpdmiscompose.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bpdmiscompose.dataClass.IndikatorDataClass
import com.example.bpdmiscompose.repositories.IndikatorKeuanganRepository
import com.example.bpdmiscompose.repositories.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IndikatorKeuanganViewModel @Inject constructor (
    private  val repository: IndikatorKeuanganRepository
): ViewModel() {
    var indikatorKeuanganUiState by mutableStateOf(IndikatorKeuanganUiState())
    val currentUser = repository.currentUser
    var hasUser by mutableStateOf(false)
    var userId by mutableStateOf("")

    init{
        viewModelScope.launch {
            hasUser = repository.hasUser()
            if(hasUser){
                userId = repository.getUserId()
                getIndikatorKeuangan()
            }
        }
    }

    fun getIndikatorKeuangan() = viewModelScope.launch {
        repository.getIndikatorKeuangan().collect() { it ->
            indikatorKeuanganUiState = indikatorKeuanganUiState.copy(indikatorKeuanganList = it)
        }
    }

    fun getIndikatorByParams(jenisKinerja : String, jenisKantor : String, tahun : Int, bulan : Int) = viewModelScope.launch {
        repository.getIndikatorByParams(jenisKinerja, jenisKantor, tahun, bulan).collect() { it ->
            indikatorKeuanganUiState = indikatorKeuanganUiState.copy(indikatorKeuanganList = it)
        }
    }

    fun addIndikatorKeuangan(kantor : String, jenisKantor: String, jenisKinerja: String,tahun : Int, bulan : Int, nominal : Double,onComplete : (Boolean) -> Unit = {}) = viewModelScope.launch {
        repository.addIndikatorKeuangan(kantor = kantor, jenisKantor = jenisKantor, jenisKinerja = jenisKinerja, tahun = tahun, bulan = bulan, nominal = nominal, onComplete = onComplete)
    }

    fun updateIndikatorKeuangan(indikatorID : String, kantor : String, jenisKantor: String, jenisKinerja: String,tahun : Int, bulan : Int, nominal : Double, onResult : (Boolean) -> Unit = {}) = viewModelScope.launch {
        repository.updateIndikatorKeuangan(indikatorID = indikatorID, kantor = kantor, jenisKantor = jenisKantor, jenisKinerja = jenisKinerja, tahun = tahun, bulan = bulan, nominal = nominal, onResult = onResult)
    }

    fun deleteIndikatorKeuangan(indikatorID: String, onComplete :(Boolean) -> Unit = {}) = viewModelScope.launch {
        repository.deleteIndikatorKeuangan(indikatorID = indikatorID, onComplete = onComplete)
    }

    fun getYearList() = viewModelScope.launch {
        repository.getYearList().collect() { it ->
            indikatorKeuanganUiState = indikatorKeuanganUiState.copy(yearList = it)
        }
    }
}

data class IndikatorKeuanganUiState(
    val indikatorKeuanganList: Resources<List<IndikatorDataClass>> = Resources.Loading(),
    val yearList : Resources<List<Int>> = Resources.Loading()
)