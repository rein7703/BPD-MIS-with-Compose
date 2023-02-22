package com.example.bpdmiscompose.screens.adminscreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bpdmiscompose.ViewModels.SkedulSetoranViewModel

@Composable
fun AdminSkedulBerdasarkanTahunLayout(skedulSetoranViewModel: SkedulSetoranViewModel) {
    val skedulSetoranUiState = skedulSetoranViewModel.skedulSetoranUiState
    val setoranList = skedulSetoranUiState.setoranList.data?.map{it} ?: emptyList()

    Column(modifier = Modifier.padding(20.dp)){
        Text(text = setoranList[0].tahun.toString(), fontWeight = FontWeight.Bold, fontSize = 30.sp)
        LazyRow(){
            // soalnya tahunnya sama aja
            item{
                Column() {
                    Text(text = "Pemegang Saham", fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                    setoranList.forEach{setoran->
                        Text(text = setoran.pemegangSaham, fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                    }
                }
            }

            item{
                Column(){
                    Text(text = "Nominal", fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                    setoranList.forEach{setoran ->
                        Text(text = "Rp ${setoran.nominal} juta", modifier = Modifier.padding(20.dp))
                    }
                }
            }
        }
    }




}