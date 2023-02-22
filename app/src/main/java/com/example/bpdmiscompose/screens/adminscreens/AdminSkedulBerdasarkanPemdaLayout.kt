package com.example.bpdmiscompose.screens.adminscreens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.bpdmiscompose.ViewModels.SkedulSetoranViewModel

@Composable

fun AdminSkedulBerdasarkanPemdaLayout(skedulSetoranViewModel: SkedulSetoranViewModel) {
    val skedulSetoranUiState = skedulSetoranViewModel.skedulSetoranUiState
    Text(text = "SETORAN BERDASARKAN Pemda : ${skedulSetoranUiState.setoranList.data?.toString()}")
}