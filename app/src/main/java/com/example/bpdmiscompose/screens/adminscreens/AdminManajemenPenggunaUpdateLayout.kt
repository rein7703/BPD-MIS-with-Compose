package com.example.bpdmiscompose.screens.adminscreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.ViewModels.AuthViewModel
import com.example.bpdmiscompose.ViewModels.UserDataViewModel
import com.example.bpdmiscompose.components.Dropdown
import com.example.bpdmiscompose.components.RadioButtonSample
import com.example.bpdmiscompose.components.TextInputBox

@Composable
fun AdminManajemenPenggunaUpdateLayout(viewModel : AuthViewModel, userDataViewModel : UserDataViewModel = hiltViewModel()){

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val dataChosen = userDataViewModel.userDataUiState.userDataChosen.data
    Log.i("TAG", "UPDATE LAYOUT: ${dataChosen.toString()}")
    val namaChosen = remember { mutableStateOf(dataChosen?.name ?: "") }
    val emailChosen = remember { mutableStateOf(dataChosen?.email ?: "") }
    val jabatanChosen = remember { mutableStateOf(dataChosen?.jabatan ?: "") }
    val statusChosen = remember { mutableStateOf(dataChosen?.status ?: false) }
    val idPegawaiChosen = remember { mutableStateOf(dataChosen?.idPegawai ?: "") }
    val nomorHPChosen = remember{ mutableStateOf(dataChosen?.nomorHP ?: "") }
    val dataIdChosen = remember { mutableStateOf(dataChosen?.dataId ?: "") }
    LazyColumn(
        modifier = Modifier
            .padding(20.dp)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ) {
        // Email
        item {
            Text(
                text = stringResource(id = R.string.email),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 10.dp)
            )
            TextInputBox(
                value = emailChosen.value,
                label = R.string.email,
                onValueChange = {emailChosen.value = it},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                focusManager = focusManager
            )
        }

        // Nama
        item {
            Text(
                text = stringResource(id = R.string.nama),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 10.dp)
            )
            TextInputBox(
                value = namaChosen.value,
                label = R.string.nama_lengkap,
                onValueChange = {namaChosen.value = it},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                focusManager = focusManager
            )
        }

        // Jabatan
        item {
            Text(
                text = stringResource(id = R.string.jabatan),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 10.dp)
            )
            val jabatanId =
                listOf(R.string.admin, R.string.komisaris, R.string.direksi, R.string.divisi)
            val jabatan = jabatanId.map { stringResource(id = it) }
            Dropdown(
                jabatan,
                label = stringResource(id = R.string.jabatan),
                default = jabatanChosen.value,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                onItemSelected = { jabatanChosen.value = it }
            )
        }

        // Id Pegawai
        item {
            Text(
                text = stringResource(id = R.string.id_pegawai),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 10.dp)
            )
            TextInputBox(
                value = idPegawaiChosen.value,
                label = R.string.id_pegawai,
                onValueChange = {idPegawaiChosen.value = it},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                focusManager = focusManager
            )
        }


        // Nomor HP
        item {
            Text(
                text = stringResource(id = R.string.nomor_hp),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 10.dp)
            )
            TextInputBox(
                value = nomorHPChosen.value,
                label = R.string.nomor_hp,
                onValueChange = {nomorHPChosen.value = it},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                focusManager = focusManager,
                keyboardType = KeyboardType.Number
            )
        }


        // Active / Not Active
        item {
            Text(
                text = stringResource(id = R.string.status_keaktifan),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 10.dp)
            )
            val aktifId = listOf(R.string.aktif, R.string.non_aktif)
            val aktif = aktifId.map { stringResource(id = it) }
            RadioButtonSample(default = statusChosen.value, radioOptions = aktif, onOptionSelected = { statusChosen.value = it == aktif[0] })
        }



        //Button
        item {
            Row() {
                Column(modifier = Modifier.weight(1f)) {
                }
                Column(modifier = Modifier.weight(1f)) {
                    val msg = stringResource(id = R.string.data_berhasil_diubah)
                    Button(
                        onClick =   {
                            try{
                                require((Regex("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")).matches(emailChosen.value)){"Email tidak valid"}
                                userDataViewModel.updateUserData(
                                    dataId = dataIdChosen.value,
                                    name = namaChosen.value,
                                    email = emailChosen.value,
                                    jabatan = jabatanChosen.value,
                                    idPegawai = idPegawaiChosen.value,
                                    nomorHp = nomorHPChosen.value,
                                    status = statusChosen.value,
                                    onComplete = {
                                        if(it){
                                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                        } else{
                                            Toast.makeText(context, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                )

                            }catch(e: Exception){
                                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                        , modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ) {
                        Text(text = stringResource(id = R.string.update))
                    }
                }
            }
        }
    }
}