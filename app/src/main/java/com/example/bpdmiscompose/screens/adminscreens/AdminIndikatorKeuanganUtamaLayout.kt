package com.example.bpdmiscompose.screens

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.ViewModels.IndikatorKeuanganViewModel
import com.example.bpdmiscompose.ViewModels.RBBViewModel
import com.example.bpdmiscompose.components.Dropdown
import com.example.bpdmiscompose.repositories.Resources
import com.example.bpdmiscompose.roboto

@Composable
fun AdminIndikatorKeuanganUtamaLayout (
    modifier: Modifier = Modifier,
    navController : NavHostController,
    indikatorKeuanganViewModel: IndikatorKeuanganViewModel,
    rbbViewModel: RBBViewModel
){
    val context = LocalContext.current
    val indikatorKeuanganUiState = indikatorKeuanganViewModel.indikatorKeuanganUiState
    val jenisKinerjaChosen = remember{ mutableStateOf("") }
    val jenisKantorChosen = remember{ mutableStateOf("") }
    val tahunChosen = remember{ mutableStateOf("") }
    val bulanChosen = remember{ mutableStateOf(0) }
    val hasRedirected = remember{ mutableStateOf(true) }
    val circularState = remember{ mutableStateOf(false) }
    val pageSelected = remember { mutableStateOf(0) } // 1 for all data // 2 for specific search result

    when(indikatorKeuanganUiState.indikatorKeuanganList){
        is Resources.Loading -> circularState.value = true
        is Resources.Success ->{
            circularState.value = false
            if(!hasRedirected.value){
                when(pageSelected.value){
                    1 -> navController.navigate(BPDMISScreen.AdminIndikatorAllData.name)
                    2 -> navController.navigate(BPDMISScreen.AdminIndikatorSearchResult.name)
                    else -> {}
                }
                hasRedirected.value = true
            }
        }
        is Resources.Error ->{
            circularState.value = false
            Log.e(ContentValues.TAG, indikatorKeuanganUiState.indikatorKeuanganList.throwable?.message.toString())
            Toast.makeText(context, indikatorKeuanganUiState.indikatorKeuanganList.throwable?.message, Toast.LENGTH_SHORT).show()
        }
    }



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
            val jenisKinerjaList = listOf(stringResource(id = R.string.aset), stringResource(id = R.string.Laba), stringResource(id = R.string.DPK), stringResource(id =  R.string.Kredit), stringResource(id = R.string.NPL))
            Text(text = stringResource(id = R.string.kinerja), fontWeight = FontWeight.Medium, modifier = Modifier.padding( start = 10.dp, top = 10.dp))
            Dropdown(
                jenisKinerjaList,
                label = stringResource(R.string.jenis_kinerja),
                default = stringResource(R.string.pilih_kinerja),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp),
                onItemSelected = { jenisKinerjaChosen.value = it }
            )
        }
        // Jenis Kantor
        item{

            val jenisKantorId = listOf(R.string.konsolidasi, R.string.cabang, R.string.capem_1, R.string.capem_2, R.string.capem_3)
            Text(text = stringResource(id = R.string.kantor), fontWeight = FontWeight.Medium, modifier = Modifier.padding( start = 10.dp))
            val jenisKantorList = jenisKantorId.map{ stringResource(id = it) }
            Dropdown(
                jenisKantorList,
                label = stringResource(id = R.string.jenis_kantor),
                default = stringResource(id = R.string.pilih_kantor),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp),
                onItemSelected = { jenisKantorChosen.value = it }
            )
        }

        // Periode Tahun
        item{
            Text(text = stringResource(id = R.string.periode), fontWeight = FontWeight.Medium, modifier = Modifier.padding( start = 10.dp))
            LaunchedEffect(key1 = Unit){indikatorKeuanganViewModel.getYearList()}
            val yearsUnsorted = indikatorKeuanganUiState.yearList.data?.map{it} ?: listOf()
            val yearsInt = yearsUnsorted.sortedDescending()
            val years = yearsInt.map{ it.toString() }
            Dropdown(
                years,
                label = stringResource(id = R.string.pilih_tahun),
                default = stringResource(id = R.string.pilih_tahun),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp),
                onItemSelected = { tahunChosen.value = it }
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
                    .padding(20.dp),
                onItemSelected = { bulanChosen.value = months.indexOf(it) + 1}
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
                    Button(onClick = {
                        try{
                            require(jenisKinerjaChosen.value != "") {"Pastikan jenis kinerja tidak kosong"}
                            require(jenisKantorChosen.value != "") {"Pastikan jenis kantor tidak kosong"}
                            require(tahunChosen.value != "") {"Pastikan tahun tidak kosong"}
                            require(bulanChosen.value != 0) {"Pastikan bulan tidak kosong"}
                            indikatorKeuanganViewModel.getIndikatorByParams(jenisKinerja = jenisKinerjaChosen.value, jenisKantor = jenisKantorChosen.value, tahun = tahunChosen.value.toInt(), bulan = bulanChosen.value)
                            indikatorKeuanganViewModel.getIndikatorByParamsPreviousYear(jenisKinerja = jenisKinerjaChosen.value, jenisKantor = jenisKantorChosen.value, tahun = tahunChosen.value.toInt() - 1, bulan = bulanChosen.value)
                            indikatorKeuanganViewModel.setJenisKinerja(jenisKinerjaChosen.value)
                            indikatorKeuanganViewModel.setJenisKantor(jenisKantorChosen.value)
                            indikatorKeuanganViewModel.setTahun(tahunChosen.value.toInt())
                            indikatorKeuanganViewModel.setTahunConst(tahunChosen.value.toInt())
                            indikatorKeuanganViewModel.setBulan(bulanChosen.value)

                            hasRedirected.value = false
                            pageSelected.value = 2
                        }catch (e: Exception) {
                            Log.e(ContentValues.TAG, "${e.message.toString()}. Mohon masukkan data dengan benar.")
                            Toast.makeText(context, "${e.message.toString()}. Mohon masukkan data dengan benar.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    ) {
                        Text(text = stringResource(id = R.string.search))
                    }

                }
            }
        }
    }
}
