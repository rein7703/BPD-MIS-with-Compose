package com.example.bpdmiscompose.screens.staffscreens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bpdmiscompose.ViewModels.SkedulSetoranViewModel

@Composable
fun StaffSkedulBerdasarkanPemda(skedulSetoranViewModel: SkedulSetoranViewModel){
    val skedulSetoranUiState = skedulSetoranViewModel.skedulSetoranUiState
    val setoranList = skedulSetoranUiState.setoranList.data?.map{it} ?: emptyList()
    LazyColumn(modifier = Modifier.padding(20.dp)){
        item {Text(text = setoranList[0].pemegangSaham, fontWeight = FontWeight.Bold, fontSize = 30.sp)}// soalnya pemegang sahamnya sama aja

        item{
            Row(){
                Text(text = "Tahun", fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                Text(text = "Nominal", fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
            }
        }
        items(setoranList){setoran->
            Row(){
                Text(text = setoran.tahun.toString(), fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                Text(text = "Rp ${setoran.nominal.toString()}juta", modifier = Modifier.padding(20.dp))
            }
        }




    }

}