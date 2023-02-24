package com.example.bpdmiscompose.screens.staffscreens

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.ViewModels.IndikatorKeuanganViewModel
import com.example.bpdmiscompose.ViewModels.RBBViewModel

@Composable
fun StaffIndikatorSearchResultScreen(
    navController:NavHostController,
    indikatorKeuanganViewModel: IndikatorKeuanganViewModel,
    rbbViewModel: RBBViewModel
    ){
    val context = LocalContext.current
    val indikatorKeuanganUiState = indikatorKeuanganViewModel.indikatorKeuanganUiState
    LaunchedEffect(key1 = Unit){
        rbbViewModel.getRBB()
    }
    val rbbUiState = rbbViewModel.rbbUiState
    Log.i(ContentValues.TAG, "Currently assessing rbb ${rbbUiState.rbbList.data.toString()}")
    val indikatorList = indikatorKeuanganUiState.indikatorKeuanganList.data?.map { it } ?: listOf()
    val indikatorPreviousYearList = indikatorKeuanganUiState.indikatorKeuanganPreviousYearList.data?.map { it } ?: listOf()
    val bulanList = listOf("Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")
    val pencapaianList =  mutableListOf<Double>()
    val pertumbuhanList = mutableListOf<Double>()



    // Main Contents

    Column(modifier = Modifier.padding(20.dp)){
        Text(text = "Kinerja ${indikatorKeuanganUiState.jenisKinerjaChosen}", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Untuk ${indikatorKeuanganUiState.jenisKantorChosen} bulan ${bulanList[indikatorKeuanganUiState.bulanChosen - 1]} tahun ${indikatorKeuanganUiState.tahunChosen}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(){
            item {
                LazyRow() {
                //Update and Delete Buttons
                item{

                }
                //Kantor
                item {

                    Column() {
                        Text(text = stringResource(id = R.string.kantor), fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        indikatorList.forEach{indikatorData ->
                            Text(text = indikatorData.kantor,
                                modifier = Modifier
                                    .padding(20.dp)

                            )
                        }
                    }
                }

                //Jenis Kantor
                item {
                    Column() {
                        Text(text = stringResource(id = R.string.jenis_kantor), fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        indikatorList.forEach{indikatorData ->
                            Text(text = indikatorData.jenisKantor,
                                modifier = Modifier
                                    .padding(20.dp)

                            )
                        }
                    }
                }


                /*
                //JENIS KINERJA, TAHUN, DAN BULAN
                //KOMPONEN INI TIDAK PERLU DITAMPILKAN
                //Namun jika berkenan ditampilkan, tinggal hapus saja '/*' dan '*/' yang ada
                item {
                    Column(){
                        Text(text = stringResource(id = R.string.jenis_kinerja), fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        indikatorList.forEach{indikatorData ->
                            Text(text = indikatorData.jenisKinerja, modifier = Modifier.padding(20.dp))
                        }
                    }

                }
                item {
                    Column() {
                        Text(text = "Tahun", fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        indikatorList.forEach{indikatorData ->
                            Text(text = indikatorData.tahun.toString(), modifier = Modifier.padding(20.dp))
                        }
                    }
                }
                item {
                    Column() {
                        Text(text = stringResource(id = R.string.bulan), fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        indikatorList.forEach{indikatorData ->
                            Text(text = bulanList[indikatorData.bulan - 1], modifier = Modifier.padding(20.dp))
                        }
                    }
                }
                */


                // Kinerja Tahun Sebelumnya
                item {
                    Column(){
                        Text(text ="Nominal ${bulanList[indikatorKeuanganUiState.bulanChosen - 1]} ${indikatorKeuanganUiState.tahunChosen - 1}", fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        pertumbuhanList.clear()
                        indikatorList.forEach{indikatorData ->
                            val hasData = remember { mutableStateOf(false) }
                            indikatorPreviousYearList.forEach{indikatorPrevious ->
                                if(indikatorData.kantor == indikatorPrevious.kantor){
                                    Text(text = indikatorPrevious.nominal.toString(),
                                        modifier = Modifier
                                            .padding(20.dp)

                                    )
                                    hasData.value = true
                                    pertumbuhanList.add(indikatorData.nominal / indikatorPrevious.nominal * 100)
                                }
                            }
                            if(!hasData.value){
                                Text(text = "0",
                                    modifier = Modifier
                                        .padding(20.dp)

                                )
                                pertumbuhanList.add(0.0)
                            }
                        }

                    }
                }


                //RBB kantor tahun tsb
                item {
                    Column(){
                        Text(text = "RBB ${indikatorKeuanganUiState.tahunChosen}", fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        var count = 0
                        pencapaianList.clear()
                        indikatorList.forEach{indikatorData ->
                            val hasData = remember { mutableStateOf(false) }
                            rbbUiState.rbbList.data?.forEach{rbb ->
                                if(rbb.tahun == indikatorKeuanganUiState.tahunChosen && rbb.kantor == indikatorData.kantor && rbb.jenisKinerja == indikatorKeuanganUiState.jenisKinerjaChosen) {
                                    Text(text = rbb.nominal.toString(), modifier = Modifier.padding(20.dp))
                                    hasData.value = true
                                    pencapaianList.add(indikatorData.nominal / rbb.nominal * 100)
                                    count++
                                    Log.i(ContentValues.TAG, "Count pencapaian: $count")
                                }
                            }
                            if(!hasData.value){
                                Text(text = "0", modifier = Modifier.padding(20.dp))
                                pencapaianList.add(0.0)
                                count++
                                Log.i(ContentValues.TAG, "Count pencapaian: $count")
                            }
                        }
                    }
                }


                // Kinerja Tahun Sekarang
                item{
                    Column() {
                        Text(text = "Nominal ${bulanList[indikatorKeuanganUiState.bulanChosen - 1]} ${indikatorKeuanganUiState.tahunChosen}", fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        indikatorList.forEach{indikatorData ->
                            Text(text = indikatorData.nominal.toString(),
                                modifier = Modifier
                                    .padding(20.dp)

                            )
                        }
                    }
                }


                // Pencapaian. Didapatkan dari persentase RBB yang berhasil didapat
                item {
                    Column (){
                        Text(text = "Pencapaian (%)", fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        Log.i(ContentValues.TAG, "pencapaianList: $pencapaianList")
                        pencapaianList.forEach { pencapaian ->
                            Text(text = "%.2f".format(pencapaian), modifier = Modifier.padding(20.dp))
                        }
                    }
                }

                // Pertumbuhan. Didapatkan dari persentase RBB yang berhasil didapat
                item {
                    Column (){
                        Text(text = "Pertumbuhan (%)", fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        Log.i(ContentValues.TAG, "pertumbuhanList: $pertumbuhanList")
                        pertumbuhanList.forEach { pertumbuhan ->
                            Text(text = "%.2f".format(pertumbuhan), modifier = Modifier.padding(20.dp))
                        }
                    }
                }
            }
            }


        }

    }


























}