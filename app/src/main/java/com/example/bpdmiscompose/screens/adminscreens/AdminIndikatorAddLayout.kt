package com.example.bpdmiscompose.screens.adminscreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.ViewModels.IndikatorKeuanganViewModel
import com.example.bpdmiscompose.ViewModels.RBBViewModel
import com.example.bpdmiscompose.components.Dropdown
import com.example.bpdmiscompose.components.TextInputBox


@Composable
fun AdminIndikatorAddLayout(indikatorKeuanganViewModel: IndikatorKeuanganViewModel, rbbViewModel: RBBViewModel){
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val tahunChosen = remember{ mutableStateOf("") }
    val bulanChosen = remember{ mutableStateOf(0) }
    val jenisKinerjaChosen = remember{ mutableStateOf("") }
    val jenisKantorChosen = remember{ mutableStateOf("") }
    val kantorChosen = remember{ mutableStateOf("") }
    val nominalChosen = remember{ mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .padding(10.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { focusManager.clearFocus() }
                )
            }
            .background(color = Color.Transparent),
    ){
        // Periode Tahun
        item{
            Text(text = stringResource(id = R.string.periode), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(
                value = tahunChosen.value,
                label = R.string.pilih_tahun,
                onValueChange = {tahunChosen.value = it},
                modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp),
                keyboardType = KeyboardType.Number,
                focusManager = focusManager,

            )
        }

        // Periode Bulan
        item {
            val monthsId = listOf(
                R.string.januari, R.string.februari,
                R.string.maret, R.string.april, R.string.mei, R.string.juni, R.string.juli, R.string.agustus, R.string.september, R.string.oktober, R.string.november, R.string.desember
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
                color = Color.White,
                onItemSelected = { bulanChosen.value = months.indexOf(it)+1 }
            )
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
                    .padding(20.dp),
                color = Color.White,
                onItemSelected = { jenisKantorChosen.value = it }
            )
        }

        // Nama Cabang
        item{
            Text(text = stringResource(id = R.string.nama_cabang_pembantu), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(
                value = kantorChosen.value,
                label = R.string.pilih_cabang,
                onValueChange = {kantorChosen.value = it},
                modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
                focusManager = focusManager)
        }


        // Nominal
        item{
            Text(text = stringResource(id = R.string.nominal), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(
                value = nominalChosen.value,
                label = R.string.dalam_juta_rupiah,
                onValueChange = {nominalChosen.value = it},
                modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
                focusManager = focusManager,
                keyboardType = KeyboardType.Number
            )
        }




        //Button
        item {
            Row(){
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)){
                    // Let this Empty
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)){

                    Button(onClick = {
                        try {
                            indikatorKeuanganViewModel.addIndikatorKeuangan(
                                kantor = kantorChosen.value,
                                bulan = bulanChosen.value,
                                tahun = tahunChosen.value.toInt(),
                                jenisKantor = jenisKantorChosen.value,
                                jenisKinerja = jenisKinerjaChosen.value,
                                nominal = nominalChosen.value.toDouble(),
                                onComplete = {
                                    Toast.makeText(context, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT).show()
                                },
                            )

                        }catch (e:Exception){
                            Log.e("Error", e.message.toString())
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                    ) {
                        Text(text = "ADD")
                    }
                }
            }
        }
    }
}