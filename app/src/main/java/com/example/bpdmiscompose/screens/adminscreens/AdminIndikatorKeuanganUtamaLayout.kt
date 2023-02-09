package com.example.bpdmiscompose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.components.Dropdown
import com.example.bpdmiscompose.roboto

@Composable
fun AdminIndikatorKeuanganUtamaLayout (modifier: Modifier = Modifier, navController : NavHostController){
    LazyColumn (
        modifier = Modifier.padding(10.dp)
    ){

        // Judul
        item {
            Text(
                text = stringResource(id = R.string.kinerja_cabang ),
                fontFamily = roboto,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = TextAlign.Start,
                fontSize = 24.sp)
        }
        //Jenis Kinerja
        item {
            val jenisKinerja = listOf(
                stringResource(id = R.string.aset), stringResource(id = R.string.Laba), stringResource(id = R.string.DPK), stringResource(id =  R.string.Kredit), stringResource(id = R.string.NPL)
            )
            Text(text = stringResource(id = R.string.kinerja), fontWeight = FontWeight.Medium, modifier = Modifier.padding( start = 10.dp, top = 10.dp))
            Dropdown(jenisKinerja,
                label = stringResource(R.string.jenis_kinerja),
                default = stringResource(R.string.pilih_kinerja),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp)
            )
        }
        // Jenis Kantor
        item{

            val jenisKantorId = listOf(
                R.string.konsolidasi, R.string.cabang, R.string.capem_1, R.string.capem_2, R.string.capem_3
            )
            Text(text = stringResource(id = R.string.kantor), fontWeight = FontWeight.Medium, modifier = Modifier.padding( start = 10.dp))
            val jenisKantor = jenisKantorId.map{ stringResource(id = it) }
            Dropdown(
                jenisKantor,
                label = stringResource(id = R.string.jenis_kantor),
                default = stringResource(id = R.string.pilih_kantor),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp)
            )
        }

        // Periode Tahun
        item{
            Text(text = stringResource(id = R.string.periode), fontWeight = FontWeight.Medium, modifier = Modifier.padding( start = 10.dp))
            val years = (2017..2023).map{it.toString()}
            Dropdown(
                years,
                label = stringResource(id = R.string.pilih_tahun),
                default = stringResource(id = R.string.pilih_tahun),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp)
            )
        }

        // Periode Bulan
        item {
            val monthsId = listOf(
                R.string.januari, R.string.februari,R.string.maret, R.string.april, R.string.mei, R.string.juni, R.string.juli, R.string.agustus, R.string.september, R.string.oktober, R.string.november, R.string.desember
            )
            val months = monthsId.map{ stringResource(id = it) }
            Dropdown(
                months,
                label = stringResource(id = R.string.pilih_bulan),
                default = stringResource(id = R.string.pilih_bulan),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp)
            )
        }

        item {
            Row(
                modifier = Modifier.padding(20.dp)
            ){
                Column(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)){

                }
                Column(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                    horizontalAlignment = Alignment.End) {
                    Button(onClick = {navController.navigate(BPDMISScreen.AdminIndikatorSearchResult.name)}) {
                        Text(text = stringResource(id = R.string.search))
                    }
                }
            }
        }
    }
}