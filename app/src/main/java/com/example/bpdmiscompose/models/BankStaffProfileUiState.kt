package com.example.bpdmiscompose.ui

data class BankStaffUiState(
    val staffName : String = "",
    val staffJabatan : String = "",
    val staffAddress : String = "",
    val staffID : String = "",
    val staffEmail : String = "",
    val staffPhoneNumber : String = "",
    var enableProfileButton : Boolean = true

)