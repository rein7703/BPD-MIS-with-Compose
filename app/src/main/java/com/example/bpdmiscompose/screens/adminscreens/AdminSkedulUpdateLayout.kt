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


@Composable
fun AdminSkedulUpdateLayout(modifier: Modifier = Modifier, skedulSetoranViewModel: SkedulSetoranViewModel) {
    val skedulSetoranUiState = skedulSetoranViewModel.skedulSetoranUiState
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val pemdaChosen = skedulSetoranUiState.pemegangSahamChosen
    val tahunChosen = skedulSetoranUiState.yearChosen
    val nominalChosen = remember { mutableStateOf(skedulSetoranUiState.nominalChosen) }
    val skedulIdChosen = remember { mutableStateOf(skedulSetoranUiState.skedulIdChosen) }
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
            val pemda = pemdaChosen
            Dropdown(
                listOf(pemdaChosen),
                label = stringResource(id = R.string.pilih_pemda),
                default = pemdaChosen,
                onItemSelected = {},
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
                value = tahunChosen,
                label = R.string.pilih_tahun,
                onValueChange = {},
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
                Text(text = "Rp", textAlign = TextAlign.Start, modifier = Modifier.weight(.15f).align(
                    Alignment.CenterVertically).padding(end = 5.dp))
                TextInputBox(
                    value = nominalChosen.value,
                    label = R.string.dalam_juta_rupiah,
                    onValueChange = {nominalChosen.value = it},
                    modifier = Modifier.weight(.7f),
                    focusManager = focusManager,
                    keyboardType = KeyboardType.Number,
                )
                Text(text = "Juta", textAlign = TextAlign.Start, modifier= Modifier.weight(.15f).align(
                    Alignment.CenterVertically).padding(start = 5.dp))
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
                            require((Regex("^\\d{1,4}\$").matches(tahunChosen))){"Tahun kosong atau tidak valid. Mohon periksa kembali."}
                            require((pemdaChosen!= "")){"Pemda kosong atau tidak valid. Mohon periksa kembali."}
                            require((Regex("^\\d{1,9}\$").matches(nominalChosen.value))){"Modal kosong atau tidak valid. Mohon pastikan data dalam juta rupiah."}
                            require(skedulIdChosen.value != null){"Tidak bisa mengubah data yang tidak ada"}
                            skedulSetoranViewModel.updateSkedulSetoran(context = context, skedulId = skedulIdChosen.value, pemegangSaham = pemdaChosen, tahun = tahunChosen.toInt(), nominal = nominalChosen.value.toLong())
                        } catch (e: Exception){
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }, modifier = Modifier
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