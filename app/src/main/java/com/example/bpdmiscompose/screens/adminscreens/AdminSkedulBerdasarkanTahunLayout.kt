package com.example.bpdmiscompose.screens.adminscreens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.bpdmiscompose.ViewModels.SkedulSetoranViewModel

@Composable
fun AdminSkedulBerdasarkanTahunLayout(skedulSetoranViewModel: SkedulSetoranViewModel) {
    val skedulSetoranUiState = skedulSetoranViewModel.skedulSetoranUiState
    Text(text = "SETORAN BERDASARKAN TAHUN : ${skedulSetoranUiState.setoranList.data?.toString()}")
}