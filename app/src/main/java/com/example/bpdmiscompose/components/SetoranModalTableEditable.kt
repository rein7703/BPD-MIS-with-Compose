package com.example.bpdmiscompose.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bpdmiscompose.R

@Composable
fun SetoranModalTableEditable(
    setoranId : List<String> = listOf("1", "2"),
    pemda: List<String> = listOf("Pemda DIY", ""),
    tahun: List<String> = listOf("2022", ""),
    modalDisetorRUPS: List<String> = listOf("999999", ""),
    komposisiRUPS: List<String> = listOf("50", ""),
    realisasiDanaSetoranModal: List<String> = listOf("999999", ""),
    totalModalDesember: List<String> = listOf("999999", ""),
    deleteData : (id: String) -> Unit = {},
    updateData : (id : String) -> Unit = {},
) {
    val openDialogDelete = remember { mutableStateOf(false) }
    val chosenId = remember{mutableStateOf("")}
    val chosenIndex = remember{mutableStateOf(0)}
    LazyRow(modifier = Modifier.padding(20.dp)) {
        item(){
            Column(modifier = Modifier.padding(end = 20.dp)){
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)) { Text(text = "Aksi\n", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center) }
                setoranId.forEach(){id ->
                    Row(){
                        // Icon for Editing Data
                        IconButton(onClick = {
                            chosenId.value = id
                            updateData(chosenId.value)
                            //Log.i(TAG, "ID yang dipilih FROM TABLE adalah: $chosenId")
                        }){
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = stringResource(R.string.EditData),
                            )
                        }


                        // Icon for Delete data
                        IconButton(onClick = { 
                            openDialogDelete.value = true 
                            chosenId.value = id
                            chosenIndex.value = setoranId.indexOf(id)
                            Log.i(TAG, "ID yang dipilih FROM TABLE adalah: $chosenId")

                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.DeleteData),
                            )
                        }
                    }
                }

            }
        }


        item() {
            Column {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(12.dp)
                ) {
                    Text(text = "Pemda\n", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
                pemda.forEach { it ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center

                        )
                    }
                }
            }
        }
        item {
            Column() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(text = "Tahun\n", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
                tahun.forEach { it ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(12.dp)
                    ) {
                        Text(text = it, fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
                    }
                }
            }
        }

        item {
            Column() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(text = "Modal Disetor RUPS\n(Juta Rp)", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
                modalDisetorRUPS.forEach { it ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(text = "Rp ${it}jt", fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
                    }
                }
            }
        }

        item {
            Column() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(text = "Komposisi RUPS \n(%)", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
                komposisiRUPS.forEach { it ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(text = "${it}%", fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
                    }
                }
            }
        }

        item {
            Column() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(text = "Realisasi Dana Setoran Modal\n(Juta Rp)", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }

                realisasiDanaSetoranModal.forEach { it ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(text = "Rp ${it}jt", fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
                    }
                }
            }
        }

        item {
            Column() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(text = "Total Modal Desember\n(Juta Rp)", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
                totalModalDesember.forEach { it ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(text = "Rp ${it}jt", fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
    if (openDialogDelete.value) {
        AlertDialog(
            title = { Text("Apakah yakin ingin menghapus data?") },
            text = { Text("Anda akan menghapus data dengan informasi: \n" +
                    "Pemda: ${pemda[chosenIndex.value]} \n" +
                    "Tahun: ${tahun[chosenIndex.value]} \n" +
                    "Modal Disetor RUPS: Rp ${modalDisetorRUPS[chosenIndex.value]} juta \n" +
                    "Komposisi RUPS: ${komposisiRUPS[chosenIndex.value]}% \n" +
                    "Realisasi Dana Setoran Modal: Rp ${realisasiDanaSetoranModal[chosenIndex.value]} juta\n" +
                    "Total Modal Desember: Rp ${totalModalDesember[chosenIndex.value]} juta \n")},
            onDismissRequest = {openDialogDelete.value = false},
            confirmButton = {
                Button(onClick = {
                    openDialogDelete.value = false
                    deleteData(chosenId.value) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)) {
                    Text("Ya, saya yakin", color = MaterialTheme.colors.onPrimary)
                }
            },
            dismissButton = {
                Button(onClick = {openDialogDelete.value = false}, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)) {
                    Text("Tidak, batalkan", color = MaterialTheme.colors.onPrimary)
                }
            }
        )
    }
}

