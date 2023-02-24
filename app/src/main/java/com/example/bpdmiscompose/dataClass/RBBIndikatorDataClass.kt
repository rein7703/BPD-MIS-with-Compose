package com.example.bpdmiscompose.dataClass

import com.google.firebase.Timestamp


data class RBBIndikatorDataClass(
    val kantor: String = "",
    val jenisKinerja: String = "",
    val tahun: Int = 0,
    val nominal: Double = 0.0,
    val timestamp: Timestamp = Timestamp.now(),
    val rbbid : String = "",
)