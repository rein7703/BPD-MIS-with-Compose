package com.example.bpdmiscompose.dataClass

import com.google.firebase.Timestamp


data class IndikatorDataClass(
    val kantor : String = "",
    val jenisKantor : String = "",
    val jenisKinerja : String = "",
    val bulan : Int = 0,
    val tahun : Int = 0,
    val nominal : Double = 0.0,
    val indikatorID : String = "",
    val timestamp: Timestamp = Timestamp.now()
)