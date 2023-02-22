package com.example.bpdmiscompose.screens.staffscreens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bpdmiscompose.ViewModels.SkedulSetoranViewModel
import com.example.bpdmiscompose.components.SkedulSetoranTableREADONLY

@Composable
fun StaffSemuaSkedulLayout(skedulSetoranViewModel: SkedulSetoranViewModel){
    val context = LocalContext.current
    val skedulSetoranUiState = skedulSetoranViewModel.skedulSetoranUiState


    LazyColumn(modifier = Modifier.padding(20.dp)){
        item{
            Text(text = "SEMUA SETORAN", fontWeight = FontWeight.Bold, fontSize = 30.sp, modifier = Modifier.padding(20.dp))
            SkedulSetoranTableREADONLY(skedulSetoranUiState.setoranList.data ?: listOf())
        }
    }
}