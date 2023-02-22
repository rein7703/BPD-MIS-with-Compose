package com.example.bpdmiscompose.screens.adminscreens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.bpdmiscompose.ViewModels.IndikatorKeuanganViewModel
import com.example.bpdmiscompose.ViewModels.RBBViewModel

@Composable
fun AdminIndikaotrAllData(indikatorKeuanganViewModel: IndikatorKeuanganViewModel, rbbViewModel: RBBViewModel){
    val indikatorKeuanganUiState = indikatorKeuanganViewModel.indikatorKeuanganUiState
    Text(text = indikatorKeuanganUiState.indikatorKeuanganList.data.toString())
}