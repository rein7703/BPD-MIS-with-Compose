package com.example.bpdmiscompose.screens.pemdascreens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bpdmiscompose.repositories.Resources
import com.example.bpdmiscompose.dataClass.SetoranModal
import com.example.bpdmiscompose.repositories.SetoranModalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PemdaViewModel @Inject constructor (
    private  val repository: SetoranModalRepository
): ViewModel() {
    var pemdaSetoranUiState by mutableStateOf(PemdaSetoranUiState())
    val currentUser = repository.currentUser
    var hasUser by mutableStateOf(false)
    var userId by mutableStateOf("")



    init {
        viewModelScope.launch {
            hasUser = repository.hasUser()
            if (hasUser) {
                userId = repository.getUserId()
            } else {
                pemdaSetoranUiState = pemdaSetoranUiState.copy(setoranChosen = Resources.Error(throwable = Throwable(message = "User is not logged in")))
            }
        }
    }

    fun getSingleSetoranModal(pemdaId : String, tahun : Int) = viewModelScope.launch {
        repository.getSingleSetoranByYearAndPemda(pemdaId, tahun).collect(){
            pemdaSetoranUiState = pemdaSetoranUiState.copy(setoranChosen = it)
        }
    }

    fun getYearList(pemdaId : String) = viewModelScope.launch {
        repository.getAllYearsByPemda(pemdaId).collect(){
            pemdaSetoranUiState = pemdaSetoranUiState.copy(yearList = it)
        }
    }
}

data class  PemdaSetoranUiState(
    var setoranChosen : Resources<SetoranModal> = Resources.Loading(),
    var yearList : Resources<List<Int>> = Resources.Loading(),
)