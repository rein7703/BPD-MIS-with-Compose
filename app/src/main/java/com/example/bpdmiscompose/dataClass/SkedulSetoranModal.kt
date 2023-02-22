package com.example.bpdmiscompose.dataClass

import com.google.firebase.Timestamp

data class SkedulSetoranModal (
    val pemegangSaham: String = "",
    val nominal: Long = 0,
    val tahun : Int = 0,
    val skedulId : String = "",
    val timestamp: Timestamp = Timestamp.now()
)