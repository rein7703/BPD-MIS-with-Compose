package com.example.bpdmiscompose.dataClass

import com.google.firebase.Timestamp


data class SetoranModal(
    val pemdaId:String = "",
    val tahun: Int = 0,
    val modalDisetorRUPS : Long = 0,
    val komposisiRUPS: Int = 0,
    val realisasiDanaSetoranModal : Long = 0,
    val totalModalDesember : Long = 0,
    val timestamp: Timestamp = Timestamp.now(),
    val documentId : String = ""
)
