package com.example.bpdmiscompose.screens.adminscreens

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.ViewModels.StaffSetoranModalViewModel
import com.example.bpdmiscompose.components.Dropdown
import com.example.bpdmiscompose.components.TextInputBox
import com.example.bpdmiscompose.dataClass.PemdaDataClass

@Composable
fun AdminSetoranUpdateLayout(staffSetoranModalViewModel: StaffSetoranModalViewModel){
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val staffSetoranModalUiState = staffSetoranModalViewModel.staffSetoranModalUiState
    var tahunChosen by remember{ mutableStateOf(staffSetoranModalUiState.setoranChosen.data?.tahun.toString()) }
    var pemdaChosen by remember{ mutableStateOf(staffSetoranModalUiState.setoranChosen.data?.pemdaId) }
    var modalChosen by remember{ mutableStateOf(staffSetoranModalUiState.setoranChosen.data?.modalDisetorRUPS.toString()) }
    var komposisiChosen by remember{ mutableStateOf(staffSetoranModalUiState.setoranChosen.data?.komposisiRUPS.toString()) }
    var realisasiChosen by remember{ mutableStateOf(staffSetoranModalUiState.setoranChosen.data?.realisasiDanaSetoranModal.toString()) }
    var totalChosen by remember{ mutableStateOf(staffSetoranModalUiState.setoranChosen.data?.totalModalDesember.toString()) }
    LazyColumn(modifier = Modifier
        .padding(10.dp)
        .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ){

        // Periode Tahun
        item{
            Text(text = stringResource(id = R.string.periode), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(value = tahunChosen, label = R.string.pilih_tahun, onValueChange = {tahunChosen = it}, modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
                keyboardType = KeyboardType.Number,
                focusManager = focusManager
            )
        }

        // Pemda
        item {
            Text(
                text = stringResource(id = R.string.pilih_pemda),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 10.dp)
            )
            val pemdaId = listOf(PemdaDataClass.DIY.title, PemdaDataClass.Yogyakarta.title, PemdaDataClass.Sleman.title, PemdaDataClass.Bantul.title, PemdaDataClass.GunungKidul.title, PemdaDataClass.KulonProgo.title)
            val pemda = pemdaId.map { stringResource(id = it) }
            Dropdown(
                pemda,
                label = stringResource(id = R.string.pilih_pemda),
                default = pemdaChosen ?: stringResource(id = R.string.pilih_pemda),
                onItemSelected = { pemdaChosen = it },
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp)
            )
        }

        // Modal Disetor RUPS
        item{
            Text(text = stringResource(id = R.string.modal_disetor_rups), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(value = modalChosen,label = R.string.dalam_juta_rupiah, onValueChange = {modalChosen = it}, modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
                focusManager = focusManager,
                keyboardType = KeyboardType.Number
            )
        }

        //Komposisi RUPS
        item{
            Text(text = stringResource(id = R.string.komposisi_rups), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(value = komposisiChosen, label = R.string.dalam_persen, onValueChange = {komposisiChosen = it}, modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
                focusManager = focusManager,
                keyboardType = KeyboardType.Number
            )
        }
        // Realisasi Dana Setoran Modal
        item{
            Text(text = stringResource(id = R.string.realisasi_dana_setoran_modal), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(value = realisasiChosen, label = R.string.dalam_juta_rupiah, onValueChange = {realisasiChosen = it}, modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
                focusManager = focusManager,
                keyboardType = KeyboardType.Number
            )
        }

        // Total Modal 31 Desember
        item{
            Text(text = stringResource(id = R.string.total_modal_31_desember), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(value = totalChosen, label = R.string.dalam_juta_rupiah, onValueChange = {totalChosen = it}, modifier = Modifier
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
                    val msg = stringResource(id = R.string.data_berhasil_diubah)
                    Button(onClick = {
                        try{
                            require((Regex("^\\d{1,4}\$").matches(tahunChosen))){"Tahun kosong atau tidak valid. Mohon periksa kembali."}
                            require((pemdaChosen != "")){"Pemda kosong atau tidak valid. Mohon periksa kembali."}
                            require((Regex("^\\d{1,9}\$").matches(modalChosen))){"Modal kosong atau tidak valid. Mohon pastikan data dalam juta rupiah."}
                            require(((komposisiChosen != "") && (komposisiChosen.toInt() < 100) && (komposisiChosen.toInt() > 0))){"Komposisi kosong atau tidak valid. Mohon pastikan data dalam persentase."}
                            require((Regex("^\\d{1,9}\$").matches(realisasiChosen))){"Realisasi kosong atau tidak valid. Mohon pastikan data dalam juta rupiah."}
                            require((Regex("^\\d{1,9}\$").matches(totalChosen))){"Total kosong atau tidak valid. Mohon pastikan data dalam juta rupiah."}

                            staffSetoranModalViewModel.updateSetoran(
                                context = context,
                                userId = staffSetoranModalViewModel.userId,
                                setoranId = staffSetoranModalUiState.setoranChosen.data?.documentId.toString(),
                                pemdaId = pemdaChosen.toString(),
                                tahun = tahunChosen.toInt(),
                                modalDisetorRUPS = modalChosen.toLong(),
                                komposisiRUPS = komposisiChosen.toInt(),
                                realisasiDanaSetoranModal = realisasiChosen.toLong(),
                                totalModalDesember = totalChosen.toLong(),
                            )



                        } catch(e: Exception){
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                    },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(text = stringResource(id = R.string.update))
                    }
                }
            }
        }
    }
}