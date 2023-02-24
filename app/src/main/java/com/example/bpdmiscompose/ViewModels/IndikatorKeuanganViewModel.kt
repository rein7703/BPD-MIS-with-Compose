package com.example.bpdmiscompose.ViewModels

import android.util.Log
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
            }
        }
    }

    fun getIndikatorKeuangan() = viewModelScope.launch {
        repository.getIndikatorKeuangan().collect() { it ->
            indikatorKeuanganUiState = indikatorKeuanganUiState.copy(indikatorKeuanganList = it)
        }
    }

    fun getIndikatorByParams(jenisKinerja : String, jenisKantor : String, tahun : Int, bulan : Int) = viewModelScope.launch {
        repository.getIndikatorByParams(jenisKinerja = jenisKinerja, jenisKantor = jenisKantor, tahun = tahun, bulan = bulan).collect() { it ->
            indikatorKeuanganUiState = indikatorKeuanganUiState.copy(indikatorKeuanganList = it)
        }
    }

    fun getIndikatorByParamsPreviousYear(jenisKinerja : String, jenisKantor : String, tahun : Int, bulan : Int) = viewModelScope.launch {
        repository.getIndikatorByParams(jenisKinerja = jenisKinerja, jenisKantor = jenisKantor, tahun = tahun, bulan = bulan).collect() { it ->
            indikatorKeuanganUiState = indikatorKeuanganUiState.copy(indikatorKeuanganPreviousYearList = it)
        }
    }

    fun getIndikatorSingular(kantor: String, jenisKantor: String, jenisKinerja: String, tahun: Int, bulan: Int) = viewModelScope.launch {
        repository.getIndikatorSingular(kantor = kantor, jenisKantor = jenisKantor, jenisKinerja = jenisKinerja, tahun = tahun, bulan = bulan).collect() { it ->
            indikatorKeuanganUiState = indikatorKeuanganUiState.copy(indikatorKeuanganSingle = it)
        }
    }

    fun addIndikatorKeuangan(kantor : String, jenisKantor: String, jenisKinerja: String,tahun : Int, bulan : Int, nominal : Double,onComplete : (Boolean) -> Unit = {}) = viewModelScope.launch {
        try{
            repository.addIndikatorKeuangan(kantor = kantor, jenisKantor = jenisKantor, jenisKinerja = jenisKinerja, tahun = tahun, bulan = bulan, nominal = nominal, onComplete = onComplete)
        } catch (e: Exception){
            e.message?.let { Log.e("Error", it) }
        }
    }

    fun updateIndikatorKeuangan(indikatorID : String, kantor : String, jenisKantor: String, jenisKinerja: String,tahun : Int, bulan : Int, nominal : Double, onResult : (Boolean) -> Unit = {}) = viewModelScope.launch {
        try{
            repository.updateIndikatorKeuangan(indikatorID = indikatorID, kantor = kantor, jenisKantor = jenisKantor, jenisKinerja = jenisKinerja, tahun = tahun, bulan = bulan, nominal = nominal, onResult = onResult)
        }
        catch (e: Exception){
            e.message?.let { Log.e("Error", it) }
        }
    }

    fun deleteIndikatorKeuangan(indikatorID: String, onComplete :(Boolean) -> Unit = {}) = viewModelScope.launch {
        repository.deleteIndikatorKeuangan(indikatorID = indikatorID, onComplete = onComplete)
    }

    fun getYearList() = viewModelScope.launch {
        repository.getYearList().collect() { it ->
            indikatorKeuanganUiState = indikatorKeuanganUiState.copy(yearList = it)
        }
    }
    fun setJenisKinerja(jenisKinerja: String) = viewModelScope.launch {
        indikatorKeuanganUiState = indikatorKeuanganUiState.copy(jenisKinerjaChosen = jenisKinerja)
    }

    fun setJenisKantor(jenisKantor: String)= viewModelScope.launch {
        indikatorKeuanganUiState = indikatorKeuanganUiState.copy(jenisKantorChosen = jenisKantor)
    }

    fun setTahun (tahun : Int) = viewModelScope.launch {
        indikatorKeuanganUiState = indikatorKeuanganUiState.copy(tahunChosen = tahun)
    }

    fun setBulan(bulan : Int) = viewModelScope.launch {
        indikatorKeuanganUiState = indikatorKeuanganUiState.copy(bulanChosen = bulan)
    }

    fun setKantor(kantor : String) = viewModelScope.launch {
        indikatorKeuanganUiState = indikatorKeuanganUiState.copy(kantorChosen = kantor)
    }

    fun setNominal(nominal : Double) = viewModelScope.launch {
        indikatorKeuanganUiState = indikatorKeuanganUiState.copy(nominalChosen = nominal)
    }

    fun setId(id : String) = viewModelScope.launch {
        indikatorKeuanganUiState = indikatorKeuanganUiState.copy(idChosen = id)
    }
    fun setTahunConst(tahun : Int) = viewModelScope.launch {
        indikatorKeuanganUiState = indikatorKeuanganUiState.copy(tahunConst = tahun)
    }
}

data class IndikatorKeuanganUiState(
    val indikatorKeuanganList: Resources<List<IndikatorDataClass>> = Resources.Loading(),
    val indikatorKeuanganSingle : Resources<IndikatorDataClass> = Resources.Loading(),
    val indikatorKeuanganPreviousYearList : Resources<List<IndikatorDataClass>> = Resources.Loading(),
    val yearList : Resources<List<Int>> = Resources.Loading(),
    val jenisKinerjaChosen : String = "",
    val jenisKantorChosen : String = "",
    val kantorChosen : String = "",
    val nominalChosen: Double = 0.0,
    val tahunChosen : Int = 0,
    val tahunConst : Int = 0,
    val bulanChosen : Int = 0,
    val idChosen : String = ""
)