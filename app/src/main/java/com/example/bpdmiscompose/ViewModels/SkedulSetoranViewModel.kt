package com.example.bpdmiscompose.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bpdmiscompose.dataClass.SkedulSetoranModal
import com.example.bpdmiscompose.repositories.Resources
import com.example.bpdmiscompose.repositories.SkedulSetoranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SkedulSetoranViewModel @Inject constructor (
    private  val repository: SkedulSetoranRepository
): ViewModel() {
    var skedulSetoranUiState by mutableStateOf(SkedulSetoranUiState())
    val currentUser = repository.currentUser
    var hasUser by mutableStateOf(false)
    var userId by mutableStateOf("")


    init{
        viewModelScope.launch {
            hasUser = repository.hasUser()
            if(hasUser){
                userId = repository.getUserId()
                getYearList()
                getPemegangSahamList()
            }
        }
    }

    fun getSkedulSetoran() = viewModelScope.launch {
        repository.getSkedulSetoran().collect() { it ->
            skedulSetoranUiState = skedulSetoranUiState.copy(setoranList = it)
        }
    }


    fun getSkedulSetoranByPemegangSaham(pemegangSaham: String) = viewModelScope.launch {
        repository.getSkedulSetoranByPemegangSaham(pemegangSaham).collect() { it ->
            skedulSetoranUiState = skedulSetoranUiState.copy(setoranList = it)
        }
    }

    fun getSkeduSetoranByTahun(tahun: Int) = viewModelScope.launch {
        repository.getSkedulSetoranByTahun(tahun).collect() { it ->
            skedulSetoranUiState = skedulSetoranUiState.copy(setoranList = it)
        }
    }

    fun getYearList() = viewModelScope.launch {
        repository.getYearList().collect() { it ->
            skedulSetoranUiState = skedulSetoranUiState.copy(yearsList = it)
        }
    }

    fun getPemegangSahamList() = viewModelScope.launch {
        repository.getPemegangSahamList().collect() { it ->
            skedulSetoranUiState = skedulSetoranUiState.copy(pemegangSahamList = it)
        }
    }

    fun addSkedulSetoran(context: Context, msg : String, pemegangSaham: String, tahun: Int, nominal : Long, onComplete: (Boolean) -> Unit = {}) = viewModelScope.launch {
        try {
            repository.addSkedulSetoran(pemegangSaham, tahun, nominal, onComplete)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        } catch(e: Exception) {
            onComplete(false)
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun updateSkedulSetoran(context: Context, pemegangSaham: String, tahun: Int, nominal : Long, skedulId : String, onResult : (Boolean) -> Unit = {}) = viewModelScope.launch {
        try{
            repository.updateSkedulSetoran(pemegangSaham, tahun, nominal, skedulId, onResult)
            Toast.makeText(context, "Data berhasil diubah",Toast.LENGTH_SHORT).show()
        } catch (e: Exception){
            onResult(false)
            Toast.makeText(context, e.message,Toast.LENGTH_SHORT).show()
        }
    }
    fun deleteSkedulSetoran(skedulId: String, onComplete: (Boolean) -> Unit) = viewModelScope.launch {
        repository.deleteSkedulSetoran(skedulId, onComplete)
    }

    fun setYearChosen(yearString : String){
        skedulSetoranUiState = skedulSetoranUiState.copy(yearChosen = yearString)
    }

    fun setPemegangSahamChosen(pemegangSahamChosen: String){
        skedulSetoranUiState = skedulSetoranUiState.copy(pemegangSahamChosen = pemegangSahamChosen)
    }

    fun setSkedulIdChosen(skedulIdChosen : String){
        skedulSetoranUiState = skedulSetoranUiState.copy(skedulIdChosen = skedulIdChosen)
    }

}

data class SkedulSetoranUiState(
    val setoranChosen : Resources<SkedulSetoranModal> = Resources.Loading(),
    val setoranList : Resources<List<SkedulSetoranModal>> = Resources.Loading(),
    val yearsList : Resources<List<Int>> = Resources.Loading(),
    val yearChosen : String = "",
    val pemegangSahamChosen : String = "",
    val nominalChosen : String = "",
    val skedulIdChosen : String = "",
    val pemegangSahamList : Resources<List<String>> = Resources.Loading(),
)