package com.example.bpdmiscompose.screens

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.ViewModels.UserDataViewModel
import com.example.bpdmiscompose.dataClass.UserDataClass

@Composable
fun AdminManajemenPenggunaLayout (modifier: Modifier = Modifier, navController:NavHostController, userDataViewModel: UserDataViewModel = hiltViewModel()){
    LaunchedEffect(key1 = Unit){
        userDataViewModel.getAllUserData()
    }
    val context = LocalContext.current
    val userDataUiState = userDataViewModel.userDataUiState
    val openDialogUpdateOrDelete = remember { mutableStateOf(false) }
    val openDialogDelete = remember { mutableStateOf(false) }
    val userChosen = remember{mutableStateOf<UserDataClass>(UserDataClass())}
    Column{
        Text(text = stringResource(id = R.string.admin_manajemen_pengguna), modifier = Modifier.padding(20.dp), fontWeight = FontWeight.Bold, fontSize = 30.sp)
        LazyColumn {
            item {
                LazyRow(){
                    item {
                        Column {
                            Text(text = stringResource(id = R.string.name), modifier = Modifier.padding(20.dp), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(20.dp))
                            userDataUiState.userDataList.data?.forEach {
                                Text(text = it.name,
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .clickable {
                                            userChosen.value = it
                                            openDialogUpdateOrDelete.value = true
                                        },

                                )
                            }
                        }
                    }
                    item {
                        Column() {
                            Text(text = stringResource(id = R.string.id_pegawai), modifier = Modifier.padding(20.dp), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(20.dp))
                            userDataUiState.userDataList.data?.forEach {
                                Text(text = it.idPegawai,
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .clickable(onClick = {
                                            userChosen.value = it
                                            openDialogUpdateOrDelete.value = true
                                        })
                                )
                            }
                        }
                    }
                    item {
                        Column {
                            Text(text = stringResource(id = R.string.jabatan), modifier = Modifier.padding(20.dp), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(20.dp))
                            userDataUiState.userDataList.data?.forEach {
                                Text(text = it.jabatan,
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .clickable(onClick = {
                                            userChosen.value = it
                                            openDialogUpdateOrDelete.value = true
                                        })
                                )
                            }
                        }
                    }
                    item {
                        Column {
                            Text(text = stringResource(id = R.string.nomor_hp), modifier = Modifier.padding(20.dp), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(20.dp))

                            userDataUiState.userDataList.data?.forEach {
                                Text(text = it.nomorHP,
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .clickable(onClick = {
                                            userChosen.value = it
                                            openDialogUpdateOrDelete.value = true
                                        })
                                )
                            }
                        }
                    }
                    item {
                        Column {
                            Text(text = stringResource(id = R.string.email), modifier = Modifier.padding(20.dp), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(20.dp))

                            userDataUiState.userDataList.data?.forEach {
                                Text(text = it.email,
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .clickable(onClick = {
                                            userChosen.value = it
                                            openDialogUpdateOrDelete.value = true
                                        })
                                )
                            }
                        }
                    }
                    item {
                        Column {
                            Text(text = stringResource(id = R.string.status), modifier = Modifier.padding(20.dp), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(20.dp))
                            userDataUiState.userDataList.data?.forEach {
                                val status = if (it.status) "Aktif" else "Tidak Aktif"
                                Text(text = status,
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .clickable(onClick = {
                                            userChosen.value = it
                                            openDialogUpdateOrDelete.value = true
                                        })
                                )
                            }
                        }
                    }
                }
            }
        }
        Row(modifier = Modifier.padding(20.dp)){
            Column(modifier = Modifier
                .fillMaxSize()
                .weight(1f)){

            }
            Column(modifier = Modifier
                .fillMaxSize()
                .weight(1f),
                horizontalAlignment = Alignment.End) {
                Button(onClick = {navController.navigate(BPDMISScreen.AdminManajemenPenggunaAdd.name)}) {
                    Text(text = stringResource(id = R.string.add))
                }
            }
        }
    }



    // Alert Dialog
    if (openDialogUpdateOrDelete.value) {
        AlertDialog(
            title = { Text("Apa yang ingin anda lakukan dengan data yang dipilih?") },
            text = { Text("Anda memilih data dengan informasi: \n" +
                    "Name = ${userChosen.value.name}\n" +
                    "ID pegawai = ${userChosen.value.idPegawai}\n" +
                    "Jabatan = ${userChosen.value.jabatan}\n" +
                    "Nomor HP = ${userChosen.value.nomorHP}\n" +
                    "Email = ${userChosen.value.email}\n" +
                    "Status = ${userChosen.value.status}\n"
            )},
            onDismissRequest = {openDialogUpdateOrDelete.value = false},
            buttons = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)){
                    Column(modifier = Modifier.weight(.6f)){}
                    Column(modifier = Modifier.weight(.4f),horizontalAlignment = Alignment.End){
                        Button(modifier = Modifier.padding(5.dp), onClick = {
                            userDataViewModel.setUserDataChosen(userChosen.value)
                            Log.i("TAG", "MANAJEMEN LAYOUT: ${userChosen.value}")
                            openDialogUpdateOrDelete.value = false
                            navController.navigate(BPDMISScreen.AdminManajemenPenggunaUpdate.name)
                        }
                        ){Text(text = "Update")}
                        Button(modifier = Modifier.padding(5.dp), onClick = {
                            openDialogUpdateOrDelete.value = false
                            openDialogDelete.value = true
                        }){Text(text = "Delete")}
                        Button(modifier = Modifier.padding(5.dp), onClick= { openDialogUpdateOrDelete.value = false }){Text(text = "Batalkan")}
                    }
                }
            }
        )
    }

    if (openDialogDelete.value) {
        AlertDialog(
            title = { Text("Apakah anda yakin ingin menghapus pengguna ini?") },
            text = { Text("Anda akan menghapus data dengan informasi: \n" +
                    "Name = ${userChosen.value.name}\n" +
                    "ID pegawai = ${userChosen.value.idPegawai}\n" +
                    "Jabatan = ${userChosen.value.jabatan}\n" +
                    "Nomor HP = ${userChosen.value.nomorHP}\n" +
                    "Email = ${userChosen.value.email}\n" +
                    "Status = ${userChosen.value.status}\n"
            )},
            onDismissRequest = {openDialogDelete.value = false},
            buttons = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)){
                    Column(modifier = Modifier.weight(.6f)){}
                    Column(modifier = Modifier.weight(.4f),horizontalAlignment = Alignment.End){
                        Button(modifier = Modifier.padding(5.dp),
                            onClick ={Toast.makeText(context, "This feature has yet to be implemented", Toast.LENGTH_SHORT).show()

                        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)){Text(text = "Hapus Sekarang", color = Color.White)}
                        Button(modifier = Modifier.padding(5.dp), onClick= { openDialogDelete.value = false }){Text(text = "Batalkan")}
                    }
                }
            }
        )
    }

}
