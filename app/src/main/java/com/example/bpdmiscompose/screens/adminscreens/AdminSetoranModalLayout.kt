package com.example.bpdmiscompose.screens

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.ViewModels.StaffSetoranModalViewModel
import com.example.bpdmiscompose.components.Dropdown
import com.example.bpdmiscompose.components.SetoranModalTableEditable
import com.example.bpdmiscompose.repositories.Resources

@Composable
fun AdminSetoranModalLayout (staffSetoranModalViewModel : StaffSetoranModalViewModel, modifier: Modifier = Modifier, navController: NavHostController){
    val staffSetoranModalUiState = staffSetoranModalViewModel.staffSetoranModalUiState
    val context = LocalContext.current
    val years = staffSetoranModalUiState.yearList.data?.map {it} ?: emptyList()
    val drawerItems = listOf("Lihat Semua Data").plus(years.sortedByDescending { it }.map{it.toString()})
    var selectedItem by remember { mutableStateOf<String>(drawerItems[0]) }
    var hasShownCircularLoading = true
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // Batas Atas LazyColumn




        //Batas atas Dropdown tahun
        item{
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






        //Batas Atas Fungsi untuk  menampilkan data
        item{

                when (staffSetoranModalUiState.setoranList) {
                    is Resources.Loading -> CircularProgressIndicator()
                    is Resources.Success -> {
                        if (selectedItem == drawerItems[0]) LaunchedEffect(key1 = Unit){
                            staffSetoranModalViewModel.loadAll()

                        } else {
                            staffSetoranModalViewModel.loadByYear(selectedItem!!.toInt())
                        }
                    }
                    else -> {
                        Log.e(
                            ContentValues.TAG,
                            staffSetoranModalUiState.setoranList.throwable?.localizedMessage.toString()
                        );
                        Toast.makeText(
                            context,
                            staffSetoranModalUiState.setoranList.throwable?.localizedMessage
                                ?: "Unknown Error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }




        // Batas Atas Tabel
        item {
            val hasRedirectedToUpdate = remember{ mutableStateOf(true) }
            val circularState = remember { mutableStateOf(false) }
            if(circularState.value && !hasShownCircularLoading){
                CircularProgressIndicator()
            }
            when(staffSetoranModalUiState.setoranChosen){
                is Resources.Loading ->{
                    circularState.value = true
                }
                is Resources.Success ->{
                    if(!hasRedirectedToUpdate.value){
                        hasRedirectedToUpdate.value = true
                        navController.navigate(BPDMISScreen.AdminSetoranUpdate.name)
                    }
                }
                else ->{
                    Log.e(
                        ContentValues.TAG,
                        staffSetoranModalUiState.setoranChosen.throwable?.localizedMessage.toString()
                    );
                    Toast.makeText(
                        context,
                        staffSetoranModalUiState.setoranChosen.throwable?.localizedMessage
                            ?: "Unknown Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            val setoranId = staffSetoranModalUiState.setoranList.data?.map{it.documentId} ?: listOf()
            val pemda = staffSetoranModalUiState.setoranList.data?.map{it.pemdaId} ?: listOf()
            val tahun = staffSetoranModalUiState.setoranList.data?.map{it.tahun.toString()} ?: listOf()
            val modalDisetorRUPS = staffSetoranModalUiState.setoranList.data?.map{it.modalDisetorRUPS.toString()} ?: listOf()
            val komposisiRUPS = staffSetoranModalUiState.setoranList.data?.map{it.komposisiRUPS.toString()} ?: listOf()
            val realisasiDanaSetoranModal = staffSetoranModalUiState.setoranList.data?.map{it.realisasiDanaSetoranModal.toString()} ?: listOf()
            val totalModalDesember = staffSetoranModalUiState.setoranList.data?.map{it.totalModalDesember.toString()} ?: listOf()
            SetoranModalTableEditable(setoranId, pemda, tahun, modalDisetorRUPS, komposisiRUPS, realisasiDanaSetoranModal, totalModalDesember,
                deleteData = { staffSetoranModalViewModel.deleteSetoran(context = context, setoranId = it) },
                updateData = {
                    staffSetoranModalUiState.setoranChosen = Resources.Loading()
                    staffSetoranModalViewModel.getSingleSetoran(setoranId = it)
                    hasRedirectedToUpdate.value = false
                }
            )
        }

        // Batas Atas tombol Add
        item{

            // Row untuk memastikan tombol ada di sebelah kanan
            Row(){


                //Memastikan bagian kiri kosong
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)){
                // Let this Empty
            }

                //Column sebelah kanan berisi tombol Add
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)){

                // Tombol Add
                Button(onClick = {
                    navController.navigate(BPDMISScreen.AdminSetoranAdd.name)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                ) {
                    Text(text = stringResource(id = R.string.add))
                }
            }
        }

        }

        //Batas Bawah LazyColumn
    }

    val openDialog = remember { mutableStateOf(false) }





}


@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        AlertDialog(
            title = { Text(title) },
            text = { Text(message) },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text("Ya, saya yakin", color = Color.Green)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Tidak, batalkan", color = Color.Red)
                }
            }
        )
    }

    Button(onClick = { openDialog.value = true }) {
        Text("Tampilkan Dialog")
    }
}


