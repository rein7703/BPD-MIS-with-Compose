package com.example.bpdmiscompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bpdmiscompose.R


@Composable
fun IndikatorAddForm(
    modifier:Modifier=Modifier
        .padding(10.dp)
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = { focusManager.clearFocus() }
            )
        }
        .background(color = Color.Transparent),
    focusManager : FocusManager,
    buttonLabel : String,
    onButtonClicked : () -> Unit = {},
){
    LazyColumn(
        modifier = modifier
    ){
        // Periode Tahun
        item{
            Text(text = stringResource(id = R.string.periode), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(label = R.string.pilih_tahun, onValueChange = {}, modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp),
                keyboardType = KeyboardType.Number,
                focusManager = focusManager
            )
        }

        // Periode Bulan
        item {
            val monthsId = listOf(
                R.string.januari, R.string.februari,
                R.string.maret, R.string.april, R.string.mei, R.string.juni, R.string.juli, R.string.agustus, R.string.september, R.string.oktober, R.string.november, R.string.desember
            )
            val months = monthsId.map{ stringResource(id = it) }
            Dropdown(
                months,
                label = stringResource(id = R.string.pilih_bulan),
                default = stringResource(id = R.string.pilih_bulan),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp),
                color = Color.White
            )
        }
        // Jenis Kantor
        item{

            val jenisKantorId = listOf(
                R.string.konsolidasi, R.string.cabang, R.string.capem_1, R.string.capem_2, R.string.capem_3
            )
            Text(text = stringResource(id = R.string.kantor), fontWeight = FontWeight.Medium, modifier = Modifier.padding( start = 10.dp))
            val jenisKantor = jenisKantorId.map{ stringResource(id = it) }
            Dropdown(
                jenisKantor,
                label = stringResource(id = R.string.jenis_kantor),
                default = stringResource(id = R.string.pilih_kantor),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp),
                color = Color.White
            )
        }

        // Nama Cabang
        item{
            Text(text = stringResource(id = R.string.nama_cabang_pembantu), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(label = R.string.pilih_cabang, onValueChange = {}, modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
                focusManager = focusManager)
        }


        // Nominal Desember Tahun Dipilih
        item{
            Text(text = stringResource(id = R.string.nominal), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(label = R.string.dalam_juta_rupiah, onValueChange = {}, modifier = Modifier
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
                        onButtonClicked()
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                    ) {
                        Text(text = buttonLabel)
                    }
                }
            }
        }
    }
}