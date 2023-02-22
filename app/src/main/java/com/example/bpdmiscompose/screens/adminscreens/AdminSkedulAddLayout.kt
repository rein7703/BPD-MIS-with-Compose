package com.example.bpdmiscompose.screens.adminscreens

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.ViewModels.SkedulSetoranViewModel
import com.example.bpdmiscompose.components.Dropdown
import com.example.bpdmiscompose.components.TextInputBox
import com.example.bpdmiscompose.dataClass.PemdaDataClass


@Composable
fun AdminSkedulAddLayout(modifier: Modifier = Modifier, skedulSetoranViewModel: SkedulSetoranViewModel) {
    val skedulSetoranUiState = skedulSetoranViewModel.skedulSetoranUiState
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val pemdaChosen = remember { mutableStateOf(skedulSetoranUiState.pemegangSahamChosen) }
    val tahunChosen = remember { mutableStateOf(skedulSetoranUiState.yearChosen) }
    val nominalChosen = remember { mutableStateOf("") }
    LazyColumn(modifier = Modifier
        .padding(10.dp)
        .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    )
    {

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
                default = pemdaChosen.value,
                onItemSelected = { pemdaChosen.value = it },
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp)
            )
        }




        // Periode Tahun
        item{
            Text(text = stringResource(id = R.string.periode), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(
                value = tahunChosen.value,
                label = R.string.pilih_tahun,
                onValueChange = {tahunChosen.value = it},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                keyboardType = KeyboardType.Number,
                focusManager = focusManager
            )
        }


        // Nominal
        item{
            Text(text = stringResource(id = R.string.nominal), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            Row (modifier = Modifier.fillMaxWidth().padding(20.dp)){
                Text(text = "Rp", textAlign = TextAlign.Start, modifier = Modifier.weight(.1f).align(Alignment.CenterVertically).padding(end = 5.dp))
                TextInputBox(
                    value = nominalChosen.value,
                    label = R.string.dalam_juta_rupiah,
                    onValueChange = {nominalChosen.value = it},
                    modifier = Modifier.weight(.6f),
                    focusManager = focusManager,
                    keyboardType = KeyboardType.Number,
                )
                Text(text = "Miliar", textAlign = TextAlign.Start, modifier= Modifier.weight(.3f).align(Alignment.CenterVertically).padding(start = 5.dp))
            }

        }

        //Button
        item{
            Row(){
                Column(modifier = Modifier.weight(1f)){
                    //leave this empty3
                    //this is for the button to be aligned to the right
                }


                // this is the column where the button resides
                Column(modifier = Modifier.weight(1f)) {
                    val msg = stringResource(id = R.string.data_berhasil_ditambahkan)
                    Button(onClick = {
                        try{
                            require((Regex("^\\d{1,4}\$").matches(tahunChosen.value))){"Tahun kosong atau tidak valid. Mohon periksa kembali."}
                            require((pemdaChosen.value != "")){"Pemda kosong atau tidak valid. Mohon periksa kembali."}
                            require((Regex("^\\d{1,9}\$").matches(nominalChosen.value))){"Modal kosong atau tidak valid. Mohon pastikan data dalam miliar rupiah."}
                            skedulSetoranViewModel.addSkedulSetoran(context, msg, pemdaChosen.value, tahunChosen.value.toInt(), nominalChosen.value.toLong())
                        } catch (e: Exception){
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }

                    }, modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                    ) {
                        Text(text = stringResource(id = R.string.add))
                    }
                }
            }


        }
    }
}