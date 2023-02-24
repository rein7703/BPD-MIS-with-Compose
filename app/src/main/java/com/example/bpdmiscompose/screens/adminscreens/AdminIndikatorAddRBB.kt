package com.example.bpdmiscompose.screens.adminscreens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.ViewModels.RBBViewModel
import com.example.bpdmiscompose.components.Dropdown
import com.example.bpdmiscompose.components.TextInputBox

@Composable
fun AdminIndikatorAddRBB(rbbViewModel: RBBViewModel = hiltViewModel()){
    val rbbUiState = rbbViewModel.rbbUiState
    val tahunChosen = remember{ mutableStateOf(rbbUiState.tahunChosen.toString()) }
    val jenisKinerjaChosen = remember { mutableStateOf(rbbUiState.jenisKinerjaChosen) }
    val kantorChosen = remember { mutableStateOf(rbbUiState.kantorChosen) }
    val nominalChosen = remember { mutableStateOf("") }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    LazyColumn(
        modifier = Modifier
            .padding(20.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { focusManager.clearFocus() }
        ) }
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

        //Jenis Kinerja
        item {
            val jenisKinerjaList = listOf(stringResource(id = R.string.aset), stringResource(id = R.string.Laba), stringResource(id = R.string.DPK), stringResource(id =  R.string.Kredit), stringResource(id = R.string.NPL))
            Text(text = stringResource(id = R.string.kinerja), fontWeight = FontWeight.Medium, modifier = Modifier.padding( start = 10.dp, top = 10.dp))
            Dropdown(
                jenisKinerjaList,
                label = stringResource(R.string.jenis_kinerja),
                default = jenisKinerjaChosen.value,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp),
                onItemSelected = { jenisKinerjaChosen.value = it }
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
                            require(kantorChosen.value != ""){"Pastikan kantor tidak kosong"}
                            require(jenisKinerjaChosen.value != ""){"Pastikan kinerja tidak kosong"}
                            require((Regex("^[1-9]\\d{3}\$").matches(tahunChosen.value))){"Pastikan input tahun yang valid"}
                            require((Regex("^\\d{1,9}\$").matches(nominalChosen.value))){"Pastikan nominal yang dimasukkan valid"}
                            rbbViewModel.addRBBByYear(
                                kantor = kantorChosen.value,
                                jenisKinerja = jenisKinerjaChosen.value,
                                tahun = tahunChosen.value.toInt(),
                                nominal = nominalChosen.value.toDouble(),
                                onComplete = {
                                    if(it){
                                        Toast.makeText(context, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Terdapat kesalahan", Toast.LENGTH_SHORT).show()
                                    }
                                }
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