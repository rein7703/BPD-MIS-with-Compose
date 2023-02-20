package com.example.bpdmiscompose.ui
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BankStaffViewModel : ViewModel() {
    private  val _uistate = MutableStateFlow(BankStaffUiState())
    val uiState : StateFlow<BankStaffUiState> =_uistate.asStateFlow()

    init {
        _uistate.value = BankStaffUiState(
            staffName = "Nama",
            staffJabatan = "Jabatan",
            staffAddress = "Alamat",
            staffEmail = "Email",
            staffPhoneNumber = "Number",
            staffID = "ID"
        )
    }
}