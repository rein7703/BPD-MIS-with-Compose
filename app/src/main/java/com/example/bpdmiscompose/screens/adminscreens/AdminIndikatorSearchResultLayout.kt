package com.example.bpdmiscompose.screens.adminscreens

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.ViewModels.IndikatorKeuanganViewModel
import com.example.bpdmiscompose.ViewModels.RBBViewModel
import com.example.bpdmiscompose.dataClass.RBBIndikatorDataClass

@Composable
fun AdminIndikatorSearchResultLayout(
    navController: NavController,
    modifier: Modifier = Modifier,
    indikatorKeuanganViewModel: IndikatorKeuanganViewModel,
    rbbViewModel: RBBViewModel
){
    val context = LocalContext.current
    val indikatorKeuanganUiState = indikatorKeuanganViewModel.indikatorKeuanganUiState
    LaunchedEffect(key1 = Unit){
        rbbViewModel.getRBB()
    }
    val rbbUiState = rbbViewModel.rbbUiState
    Log.i(TAG, "Currently assessing rbb ${rbbUiState.rbbList.data.toString()}")
    val indikatorList = indikatorKeuanganUiState.indikatorKeuanganList.data?.map { it } ?: listOf()
    val indikatorPreviousYearList = indikatorKeuanganUiState.indikatorKeuanganPreviousYearList.data?.map { it } ?: listOf()
    val bulanList = listOf("Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")
    val pencapaianList =  mutableListOf<Double>()
    val pertumbuhanList = mutableListOf<Double>()
    val openDialogDeleteOrUpdate = remember { mutableStateOf(false) }
    val openDialogDelete = remember{mutableStateOf(false)}
    val openDialogAdd = remember{ mutableStateOf(false) }
    val openDialogRBBUpdateOrDelete = remember { mutableStateOf(false) }
    val openDialogRBBDelete = remember { mutableStateOf(false) }
    val openDialogRBBAdd = remember { mutableStateOf(false) }
    val kantorChosen = remember{ mutableStateOf("") }
    val jenisKantorChosen = remember{ mutableStateOf("") }
    val nominalChosen = remember{ mutableStateOf("") }
    val jenisKinerjaChosen = remember{ mutableStateOf("") }
    val nominalPreviousYearChosen = remember{ mutableStateOf("") }
    val RBBChosen = remember{ mutableStateOf<RBBIndikatorDataClass?>(null) }
    val idChosen = remember{ mutableStateOf("") }
    val tahunChosen = remember { mutableStateOf("") }


    // Main Contents

    Column(modifier = Modifier.padding(20.dp)){
        Text(text = "Kinerja ${indikatorKeuanganUiState.jenisKinerjaChosen}", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Untuk ${indikatorKeuanganUiState.jenisKantorChosen} bulan ${bulanList[indikatorKeuanganUiState.bulanChosen - 1]} tahun ${indikatorKeuanganUiState.tahunChosen}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = "Silahkan klik salah satu data untuk mengubah atau menghapusnya", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(){
            item {
                LazyRow() {

                //Kantor
                item {

                    Column() {
                        Text(text = stringResource(id = R.string.kantor), fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        indikatorList.forEach{indikatorData ->
                            Text(text = indikatorData.kantor,
                                modifier = Modifier
                                    .padding(20.dp)
                                    .clickable(onClick = {
                                        openDialogDeleteOrUpdate.value = true
                                        kantorChosen.value = indikatorData.kantor
                                        jenisKantorChosen.value = indikatorData.jenisKantor
                                        nominalChosen.value = indikatorData.nominal.toString()
                                        idChosen.value = indikatorData.indikatorID.toString()
                                        tahunChosen.value = indikatorData.tahun.toString()
                                    })
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
                                    .clickable(onClick = {
                                        openDialogDeleteOrUpdate.value = true
                                        kantorChosen.value = indikatorData.kantor
                                        jenisKantorChosen.value = indikatorData.jenisKantor
                                        nominalChosen.value = indikatorData.nominal.toString()
                                        idChosen.value = indikatorData.indikatorID.toString()
                                        tahunChosen.value = indikatorData.tahun.toString()

                                    })
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
                                            .clickable(onClick = {
                                                openDialogDeleteOrUpdate.value = true
                                                kantorChosen.value = indikatorPrevious.kantor
                                                jenisKantorChosen.value = indikatorPrevious.jenisKantor
                                                nominalChosen.value = indikatorPrevious.nominal.toString()
                                                idChosen.value = indikatorPrevious.indikatorID
                                                tahunChosen.value = indikatorPrevious.tahun.toString()
                                            })
                                    )
                                    hasData.value = true
                                    pertumbuhanList.add(
                                        (indikatorData.nominal - indikatorPrevious.nominal)/ indikatorPrevious.nominal * 100
                                    )
                                }
                            }
                            if(!hasData.value){
                                Text(text = "0",
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .clickable(onClick = {
                                            openDialogAdd.value = true
                                            kantorChosen.value = indikatorData.kantor
                                            jenisKantorChosen.value = indikatorData.jenisKantor
                                            tahunChosen.value = (indikatorData.tahun - 1).toString()
                                        })
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
                                    Text(text = rbb.nominal.toString(),
                                        modifier = Modifier
                                            .padding(20.dp)
                                            .clickable(onClick = {
                                                openDialogRBBUpdateOrDelete.value = true
                                                RBBChosen.value = rbb
                                                Log.i(TAG, "RBBChosen: $RBBChosen")
                                            })

                                    )
                                    hasData.value = true
                                    pencapaianList.add(indikatorData.nominal / rbb.nominal * 100)
                                    count++
                                    Log.i(TAG, "Count pencapaian: $count")
                                }
                            }
                            if(!hasData.value){
                                Text(text = "0",
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .clickable(onClick = {
                                            openDialogRBBAdd.value = true
                                            kantorChosen.value = indikatorData.kantor
                                            tahunChosen.value = indikatorData.tahun.toString()
                                            jenisKinerjaChosen.value = indikatorKeuanganUiState.jenisKinerjaChosen
                                        }
                                ))
                                pencapaianList.add(0.0)
                                count++
                                Log.i(TAG, "Count pencapaian: $count")
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
                                    .clickable(onClick = {
                                        openDialogDeleteOrUpdate.value = true
                                        kantorChosen.value = indikatorData.kantor
                                        jenisKantorChosen.value = indikatorData.jenisKantor
                                        nominalChosen.value = indikatorData.nominal.toString()
                                        idChosen.value = indikatorData.indikatorID
                                        tahunChosen.value = indikatorData.tahun.toString()

                                    })
                            )
                        }
                    }
                }


                // Pencapaian. Didapatkan dari persentase RBB yang berhasil didapat
                item {
                    Column (){
                        Text(text = "Pencapaian (%)", fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        Log.i(TAG, "pencapaianList: $pencapaianList")
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
                        Log.i(TAG, "pertumbuhanList: $pertumbuhanList")
                        pertumbuhanList.forEach { pertumbuhan ->
                            Text(text = "%.2f".format(pertumbuhan), modifier = Modifier.padding(20.dp))
                        }
                    }
                }
            }
            }


            // Buttons
            item {Row(){
                Column(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)){

                }
                Column(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                    horizontalAlignment = Alignment.End) {
                    //Add Data
                    Button(onClick = {
                        indikatorKeuanganViewModel.setTahun(indikatorKeuanganUiState.tahunConst)
                        indikatorKeuanganViewModel.setKantor("")
                        navController.navigate(BPDMISScreen.AdminIndikatorAdd.name)
                    }) {
                        Text(text = stringResource(id = R.string.add))
                    }

                }
            }}

        }


        if (openDialogDeleteOrUpdate.value) {
            AlertDialog(
                title = { Text("Apa yang ingin anda lakukan dengan data yang dipilih?") },
                text = { Text("Anda memilih data dengan informasi: \n" +
                        "Kantor = ${kantorChosen.value}\n" +
                        "Jenis Kantor = ${jenisKantorChosen.value}\n" +
                        "Jenis Kinerja = ${indikatorKeuanganUiState.jenisKinerjaChosen}\n" +
                        "Nominal = ${nominalChosen.value}\n" +
                        "Tahun = ${tahunChosen.value}\n"
                )},
                onDismissRequest = {openDialogDeleteOrUpdate.value = false},
                buttons = {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)){
                        Column(modifier = Modifier.weight(.6f)){}
                        Column(modifier = Modifier.weight(.4f),horizontalAlignment = Alignment.End){
                            Button(modifier = Modifier.padding(5.dp), onClick = {
                                indikatorKeuanganViewModel.setKantor(kantorChosen.value)
                                indikatorKeuanganViewModel.setJenisKantor(jenisKantorChosen.value)
                                indikatorKeuanganViewModel.setTahun(tahunChosen.value.toInt())
                                indikatorKeuanganViewModel.setKantor(kantorChosen.value)
                                indikatorKeuanganViewModel.setNominal(nominalChosen.value.toDouble())
                                indikatorKeuanganViewModel.setId(idChosen.value)
                                openDialogDeleteOrUpdate.value = false
                                navController.navigate(BPDMISScreen.AdminIndikatorUpdateLayout.name)
                            }
                            ){Text(text = "Update")}
                            Button(modifier = Modifier.padding(5.dp), onClick = {
                                openDialogDeleteOrUpdate.value = false
                                openDialogDelete.value = true
                            }){Text(text = "Delete")}
                            Button(modifier = Modifier.padding(5.dp), onClick= { openDialogDeleteOrUpdate.value = false }){Text(text = "Batalkan")}
                        }
                    }
                }
            )
        }

        if (openDialogDelete.value) {
            AlertDialog(
                title = { Text("Apakah anda yakin ingin menghapus data ini?") },
                text = { Text("Anda akan menghapus data dengan informasi: \n" +
                        "Kantor = ${kantorChosen.value}\n" +
                        "Jenis Kantor = ${jenisKantorChosen.value}\n" +
                        "Jenis Kinerja = ${indikatorKeuanganUiState.jenisKinerjaChosen}\n" +
                        "Nominal = ${nominalChosen.value}\n" +
                        "Tahun = ${tahunChosen.value}\n" )},
                onDismissRequest = {openDialogDelete.value = false},
                buttons = {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)){
                        Column(modifier = Modifier.weight(.6f)){}
                        Column(modifier = Modifier.weight(.4f),horizontalAlignment = Alignment.End){
                            Button(modifier = Modifier.padding(5.dp), onClick = {
                                openDialogDelete.value = false
                                try {
                                    indikatorKeuanganViewModel.deleteIndikatorKeuangan(idChosen.value, onComplete = {
                                        if(it){
                                            Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(context, "Data gagal dihapus", Toast.LENGTH_SHORT).show()
                                        }
                                    })
                                }catch (e: Exception){
                                    Log.e(TAG, "Error: ${e.message}")
                                }

                                }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)){Text(text = "Hapus Sekarang", color = Color.White)}
                            Button(modifier = Modifier.padding(5.dp), onClick= { openDialogDelete.value = false }){Text(text = "Batalkan")}
                        }
                    }
                }
            )
        }

        if (openDialogAdd.value) {
            AlertDialog(
                title = { Text("Data yang anda pilih masih kosong. Apakah anda ingin menambahkan data?") },
                text = { Text("Anda akan menambah data untuk: \n" +
                        "Kantor: ${kantorChosen.value}\n" +
                        "Jenis Kantor: ${jenisKantorChosen.value}\n" +
                        "Jenis Kinerja: ${jenisKinerjaChosen.value}\n" +
                        "Tahun: ${tahunChosen.value}\n"
                        )},
                onDismissRequest = {openDialogAdd.value = false},
                buttons = {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)){
                        Column(modifier = Modifier.weight(.6f)){}
                        Column(modifier = Modifier.weight(.4f),horizontalAlignment = Alignment.End){
                            Button(modifier = Modifier.padding(5.dp), onClick = {
                                indikatorKeuanganViewModel.setKantor(kantorChosen.value)
                                indikatorKeuanganViewModel.setJenisKantor(jenisKantorChosen.value)
                                indikatorKeuanganViewModel.setTahun(tahunChosen.value.toInt())
                                indikatorKeuanganViewModel.setKantor(kantorChosen.value)
                                openDialogAdd.value = false
                                navController.navigate(BPDMISScreen.AdminIndikatorAdd.name)
                            },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)){Text(text = "Tambahkan Data", color = Color.White)}
                            Button(modifier = Modifier.padding(5.dp), onClick= { openDialogAdd.value = false }){Text(text = "Batalkan")}
                        }
                    }
                }
            )
        }


        if(openDialogRBBAdd.value){
            AlertDialog(
                title = { Text("RBB yang anda pilih masih kosong. Apakah anda ingin menambahkan data?") },
                text = { Text("Anda akan menambah data untuk: \n" +
                        "Kantor: ${kantorChosen.value}\n" +
                        "Jenis Kinerja: ${jenisKinerjaChosen.value}\n" +
                        "Tahun: ${tahunChosen.value}\n"
                )},
                onDismissRequest = {openDialogRBBAdd.value = false},
                buttons = {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)){
                        Column(modifier = Modifier.weight(.6f)){}
                        Column(modifier = Modifier.weight(.4f),horizontalAlignment = Alignment.End){
                            Button(modifier = Modifier.padding(5.dp), onClick = {
                                rbbViewModel.setKantorChosen(kantorChosen.value)
                                rbbViewModel.setJenisKinerjaChosen(jenisKinerjaChosen.value)
                                rbbViewModel.setTahunChosen(tahunChosen.value.toInt())
                                openDialogRBBAdd.value = false
                                navController.navigate(BPDMISScreen.AdminIndikatorRBBAdd.name)
                            },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)){Text(text = "Tambahkan Data", color = Color.White)}
                            Button(modifier = Modifier.padding(5.dp), onClick= { openDialogRBBAdd.value = false }){Text(text = "Batalkan")}
                        }
                    }
                }
            )
        }


        if (openDialogRBBUpdateOrDelete.value) {
            AlertDialog(
                title = { Text("Apa yang ingin anda lakukan dengan data RBB yang dipilih?") },
                text = { Text("Anda memilih RBB dengan informasi: \n" +
                        "Kantor = ${RBBChosen.value?.kantor}\n" +
                        "Jenis Kinerja = ${RBBChosen.value?.jenisKinerja}\n" +
                        "Nominal = ${RBBChosen.value?.nominal}\n" +
                        "Tahun = ${RBBChosen.value?.tahun}\n"
                )},
                onDismissRequest = {openDialogRBBUpdateOrDelete.value = false},
                buttons = {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)){
                        Column(modifier = Modifier.weight(.6f)){}
                        Column(modifier = Modifier.weight(.4f),horizontalAlignment = Alignment.End){
                            Button(modifier = Modifier.padding(5.dp), onClick = {
                                try{
                                    RBBChosen.value?.let { rbbViewModel.setRBBChosen(it) }
                                }catch(e: Exception){
                                    Log.e(TAG, "Error: ${e.message}")
                                    Log.e(TAG, "Error: ${e.message}")
                                }
                                openDialogRBBUpdateOrDelete.value = false
                                navController.navigate(BPDMISScreen.AdminIndikatorRBBUpdate.name)
                            }
                            ){Text(text = "Update")}
                            Button(modifier = Modifier.padding(5.dp), onClick = {
                                openDialogRBBUpdateOrDelete.value = false
                                openDialogRBBDelete.value = true
                            }){Text(text = "Delete")}
                            Button(modifier = Modifier.padding(5.dp), onClick= { openDialogRBBUpdateOrDelete.value = false }){Text(text = "Batalkan")}
                        }
                    }
                }
            )
        }


        if (openDialogRBBDelete.value) {
            AlertDialog(
                title = { Text("Apakah anda yakin ingin menghapus data ini?") },
                text = { Text("Anda akan menghapus data dengan informasi: \n" +
                        "Kantor = ${RBBChosen.value?.kantor}\n" +
                        "Jenis Kinerja = ${RBBChosen.value?.jenisKinerja}\n" +
                        "Nominal = ${RBBChosen.value?.nominal}\n" +
                        "Tahun = ${RBBChosen.value?.tahun}\n"
                )},
                onDismissRequest = {openDialogRBBDelete.value = false},
                buttons = {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)){
                        Column(modifier = Modifier.weight(.6f)){}
                        Column(modifier = Modifier.weight(.4f),horizontalAlignment = Alignment.End){
                            Button(modifier = Modifier.padding(5.dp), onClick = {
                                openDialogRBBDelete.value = false
                                try {
                                    RBBChosen.value?.rbbid?.let {
                                        rbbViewModel.deleteRBB(it, onComplete = {isComplete ->
                                            if(isComplete){
                                                Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                                            } else {
                                                Toast.makeText(context, "Data gagal dihapus", Toast.LENGTH_SHORT).show()
                                            }
                                        })
                                    }
                                }catch (e: Exception){
                                    Log.e(TAG, "Error: ${e.message}")
                                }

                            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)){Text(text = "Hapus Sekarang", color = Color.White)}
                            Button(modifier = Modifier.padding(5.dp), onClick= { openDialogRBBDelete.value = false }){Text(text = "Batalkan")}
                        }
                    }
                }
            )
        }

    }


























}



