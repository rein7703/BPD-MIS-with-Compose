package com.example.bpdmiscompose.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bpdmiscompose.dataClass.SkedulSetoranModal


data class SkedulClassForEditableTable(
    val pemegangSaham: String,
    val ListTahunNominal : List<List<String>>
)


val exampleSkedulSetoranList =listOf(
    SkedulSetoranModal("Pemda 1", 2021, 100, "1"),
    SkedulSetoranModal("Pemda 1", 2022, 200, "2"),
)
@Composable
fun SkedulSetoranTableEditable(
    skedulSetoranList: List<SkedulSetoranModal> = exampleSkedulSetoranList,
    onDelete : (id : String) -> Unit = {},
    onUpdate : (id : String) -> Unit = {},
    onAdd : () -> Unit = {}
) {
    Log.i(TAG, "START!!!")
    val tahunUnsorted= skedulSetoranList.map{it.tahun.toString()}.distinct() ?: emptyList()
    val tahunList = tahunUnsorted.sortedDescending()
    val pemdaList = skedulSetoranList.map{it.pemegangSaham}.distinct()
    val skedList = mutableListOf<SkedulClassForEditableTable>()
    val openDialogDeleteOrUpdate = remember { mutableStateOf(false) }
    val openDialogDelete = remember{mutableStateOf(false)}
    val openDialogAdd = remember{ mutableStateOf(false) }
    val pemegangSahamChosen = remember{ mutableStateOf("") }
    val skedulIdChosen = remember{ mutableStateOf("") }
    val tahunChosen = remember{ mutableStateOf("") }
    val nominalChosen = remember{ mutableStateOf("") }
    pemdaList.forEach{pemda->
        val groupList = mutableListOf<List<String>>()
        skedulSetoranList.forEach{skedulSetoranModal ->
            if(skedulSetoranModal.pemegangSaham == pemda){
                groupList.add(listOf(
                    skedulSetoranModal.tahun.toString(),
                    skedulSetoranModal.nominal.toString(),
                    skedulSetoranModal.skedulId.toString()
                ))
            }
        }
        skedList.add(SkedulClassForEditableTable(pemda, groupList))
    }
    Log.i(TAG, "${skedList.toString()}")

    LazyRow() {
        
        item() {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Pemegang Saham",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(40.dp))
                pemdaList.forEach() {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
                Text(text = "Total Modal",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp)
                )

            }
        }

        items(tahunList){tahun ->
            var count = 0
            var totalCapitalPerYear = 0
            Log.i(TAG, "Tahun yang ada adalah $tahunList")
            Log.i(TAG, "Tahun yang dipilih adalah $tahun")
            Column(modifier = Modifier.padding(20.dp)){
                Text(
                    text = tahun,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(40.dp))
                pemdaList.forEach{pemda ->
                    val isThereData = remember{ mutableStateOf(false) }
                    skedList.forEach {  sked->
                        count++
                        Log.i(TAG, "This is row ${sked.toString()}")
                        if (sked.pemegangSaham == pemda){
                            sked.ListTahunNominal.forEach{it ->
                                if(it[0] == tahun){
                                    Text(
                                        text = "Rp ${it[1]}juta",
                                        fontWeight = FontWeight.Normal,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .clickable(onClick = {
                                                Log.i(
                                                    TAG,
                                                    "You are clicking year ${it[0]} nominal ${it[1]} with id ${it[2]}"
                                                )
                                                pemegangSahamChosen.value = sked.pemegangSaham
                                                tahunChosen.value = it[0]
                                                nominalChosen.value = it[1]
                                                skedulIdChosen.value = it[2]
                                                openDialogDeleteOrUpdate.value = true
                                            }
                                            )
                                    )
                                    isThereData.value = true
                                    totalCapitalPerYear += it[1].toInt()
                                }
                            }
                        }
                    }
                    if(!isThereData.value){
                        Text(
                            text = "EMPTY",
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable(onClick = {
                                    pemegangSahamChosen.value = pemda
                                    tahunChosen.value = tahun
                                    openDialogAdd.value = true
                                })
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
                Text(text = "Rp ${totalCapitalPerYear.toString()}juta",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }



    if (openDialogDeleteOrUpdate.value) {
        AlertDialog(
            title = { Text("Apa yang ingin anda lakukan dengan data yang dipilih?") },
            text = { Text("Anda memilih data dengan informasi: \n" +
                    "Pemegang Saham: ${pemegangSahamChosen.value}\n" +
                    "Tahun: ${tahunChosen.value}\n" +
                    "Nominal: Rp ${nominalChosen.value}juta\n")},
            onDismissRequest = {openDialogDeleteOrUpdate.value = false},
            buttons = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)){
                    Column(modifier = Modifier.weight(.6f)){}
                    Column(modifier = Modifier.weight(.4f),horizontalAlignment = Alignment.End){
                        Button(modifier = Modifier.padding(5.dp), onClick = {
                            openDialogDeleteOrUpdate.value = false
                            onUpdate(skedulIdChosen.value)}
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
            title = { Text("Apa yang ingin anda lakukan dengan data yang dipilih?") },
            text = { Text("Anda memilih data dengan informasi: \n" +
                    "Pemegang Saham: ${pemegangSahamChosen.value}\n" +
                    "Tahun: ${tahunChosen.value}\n" +
                    "Nominal: Rp ${nominalChosen.value}juta\n")},
            onDismissRequest = {openDialogDelete.value = false},
            buttons = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)){
                    Column(modifier = Modifier.weight(.6f)){}
                    Column(modifier = Modifier.weight(.4f),horizontalAlignment = Alignment.End){
                        Button(modifier = Modifier.padding(5.dp), onClick = {
                            openDialogDelete.value = false
                            onDelete(skedulIdChosen.value
                            )}, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)){Text(text = "Hapus Sekarang", color = Color.White)}
                        Button(modifier = Modifier.padding(5.dp), onClick= { openDialogDelete.value = false }){Text(text = "Batalkan")}
                    }
                }
            }
        )
    }


    if (openDialogAdd.value) {
        AlertDialog(
            title = { Text("Apakah anda ingin menambahkan data?") },
            text = { Text("Anda mendambah data untuk pemegang saham dan tahun: \n" +
                    "Pemegang Saham: ${pemegangSahamChosen.value}\n" +
                    "Tahun: ${tahunChosen.value}\n" )},
            onDismissRequest = {openDialogAdd.value = false},
            buttons = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)){
                    Column(modifier = Modifier.weight(.6f)){}
                    Column(modifier = Modifier.weight(.4f),horizontalAlignment = Alignment.End){
                        Button(modifier = Modifier.padding(5.dp), onClick = {
                            openDialogAdd.value = false
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)){Text(text = "Tambahkan Data", color = Color.White)}
                        Button(modifier = Modifier.padding(5.dp), onClick= { openDialogAdd.value = false }){Text(text = "Batalkan")}
                    }
                }
            }
        )
    }



}



