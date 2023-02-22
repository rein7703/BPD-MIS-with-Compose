package com.example.bpdmiscompose.screens

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.components.Dropdown
import com.example.bpdmiscompose.repositories.Resources
import com.example.bpdmiscompose.dataClass.SetoranModal
import com.example.bpdmiscompose.roboto
import com.example.bpdmiscompose.screens.pemdascreens.PemdaViewModel


@Composable
fun PemdaSetoranSahamScreen(
    nama : String = "nama",
    pemdaViewModel: PemdaViewModel = hiltViewModel(),
){
    val pemdaSetoranUiState = pemdaViewModel.pemdaSetoranUiState
    pemdaViewModel.getYearList("Pemprov DIY")
    val drawerItems =
        listOf("Pilih Tahun").plus(pemdaSetoranUiState.yearList.data?.map { it.toString() }
            ?: emptyList())
    var selectedItem by remember { mutableStateOf<String>(drawerItems[0]) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Text(text = "Selamat Datang, ${nama}!", fontFamily = roboto, fontWeight = FontWeight.Medium, color = Color.Black, textAlign = TextAlign.Start, fontSize = 36.sp)
        }
        item {
            Dropdown(
                drawerItems,
                label = stringResource(R.string.tahun_setoran_modal),
                default = drawerItems[0],
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp),
                onItemSelected = {
                    selectedItem = it
                    Log.i(ContentValues.TAG, "the selected item is ${selectedItem.toString()}")
                }
            )
        }

        item {
            if(selectedItem != drawerItems[0]) {
                pemdaViewModel.getSingleSetoranModal("Pemprov DIY", selectedItem.toInt())
                when (pemdaSetoranUiState.setoranChosen) {
                    is Resources.Loading -> CircularProgressIndicator()
                    is Resources.Success -> {
                        SetoranSahamCard(pemdaSetoranUiState.setoranChosen.data)
                    }
                    is Resources.Error -> {
                        Text(text = "Error", color = Color.Red)
                    }
                }
            }
        }
    }










}
@Preview(showBackground = true)
@Composable
fun SetoranSahamCard(
    setoranModal : SetoranModal?= null,
){

    val pemdaId = setoranModal?.pemdaId ?: "pemdaId"
    val tahun = setoranModal?.tahun ?: "tahun"
    val modalDisetor = setoranModal?.modalDisetorRUPS ?: "modalDisetor"
    val komposisiRUPS = setoranModal?.komposisiRUPS ?: "komposisiRUPS"
    val realisasiDana = setoranModal?.realisasiDanaSetoranModal ?: "realisasiDana"
    val totalModal = setoranModal?.totalModalDesember ?: "totalModal"
    Column(modifier = Modifier
        .padding(20.dp)
        .background(color = MaterialTheme.colors.primary)) {
        Text(text = "Pemda ID: ${pemdaId}", fontFamily = roboto, fontWeight = FontWeight.Medium, color = Color.White, textAlign = TextAlign.Start, fontSize = 24.sp, modifier = Modifier.padding(20.dp))
        Text(text = "Tahun: ${tahun}", fontFamily = roboto, fontWeight = FontWeight.Medium, color = Color.White, textAlign = TextAlign.Start, fontSize = 24.sp, modifier = Modifier.padding(20.dp))
        Text(text = "Modal Disetor: ${modalDisetor}", fontFamily = roboto, fontWeight = FontWeight.Medium, color = Color.White, textAlign = TextAlign.Start, fontSize = 24.sp, modifier = Modifier.padding(20.dp))
        Text(text = "Komposisi RUPS: ${komposisiRUPS}", fontFamily = roboto, fontWeight = FontWeight.Medium, color = Color.White, textAlign = TextAlign.Start, fontSize = 24.sp, modifier = Modifier.padding(20.dp))
        Text(text = "Realisasi Dana: ${realisasiDana}", fontFamily = roboto, fontWeight = FontWeight.Medium, color = Color.White, textAlign = TextAlign.Start, fontSize = 24.sp, modifier = Modifier.padding(20.dp))
        Text(text = "Total Modal: ${totalModal}", fontFamily = roboto, fontWeight = FontWeight.Medium, color = Color.White, textAlign = TextAlign.Start, fontSize = 24.sp, modifier = Modifier.padding(20.dp))
    }

}