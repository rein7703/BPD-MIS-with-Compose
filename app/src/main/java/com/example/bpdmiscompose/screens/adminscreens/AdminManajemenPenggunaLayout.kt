package com.example.bpdmiscompose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.ViewModels.UserDataViewModel

@Composable
fun AdminManajemenPenggunaLayout (modifier: Modifier = Modifier, navController:NavHostController, userDataViewModel: UserDataViewModel = hiltViewModel()){
    LaunchedEffect(key1 = Unit){
        userDataViewModel.getAllUserData()
    }
    val userDataUiState = userDataViewModel.userDataUiState
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
                                Text(text = it.name, modifier = Modifier.padding(20.dp))
                            }
                        }
                    }
                    item {
                        Column() {
                            Text(text = stringResource(id = R.string.id_pegawai), modifier = Modifier.padding(20.dp), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(20.dp))
                            userDataUiState.userDataList.data?.forEach {
                                Text(text = it.idPegawai, modifier = Modifier.padding(20.dp))
                            }
                        }
                    }
                    item {
                        Column {
                            Text(text = stringResource(id = R.string.jabatan), modifier = Modifier.padding(20.dp), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(20.dp))
                            userDataUiState.userDataList.data?.forEach {
                                Text(text = it.jabatan, modifier = Modifier.padding(20.dp))
                            }
                        }
                    }
                    item {
                        Column {
                            Text(text = stringResource(id = R.string.nomor_hp), modifier = Modifier.padding(20.dp), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(20.dp))

                            userDataUiState.userDataList.data?.forEach {
                                Text(text = it.nomorHP, modifier = Modifier.padding(20.dp))
                            }
                        }
                    }
                    item {
                        Column {
                            Text(text = stringResource(id = R.string.email), modifier = Modifier.padding(20.dp), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(20.dp))

                            userDataUiState.userDataList.data?.forEach {
                                Text(text = it.email, modifier = Modifier.padding(20.dp))
                            }
                        }
                    }
                    item {
                        Column {
                            Text(text = stringResource(id = R.string.status), modifier = Modifier.padding(20.dp), fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(20.dp))
                            userDataUiState.userDataList.data?.forEach {
                                val status = if (it.status) "Aktif" else "Tidak Aktif"
                                Text(text = status, modifier = Modifier.padding(20.dp))
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
}
