package com.example.bpdmiscompose.screens.adminscreens

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.bpdmiscompose.components.Dropdown
import com.example.bpdmiscompose.components.TextInputBox

@Composable
fun AdminSetoranAddLayout(){
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    LazyColumn(modifier = Modifier
        .padding(10.dp)
        .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ){
        // Periode Tahun
        item{
            Text(text = stringResource(id = R.string.periode), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(label = R.string.pilih_tahun, onValueChange = {}, modifier = Modifier
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
            val pemdaId = listOf(R.string.pemerintah_kabupaten_bantul, R.string.pemerintah_kabupaten_gunungkidul, R.string.pemerintah_kabupaten_kulonprogo, R.string.pemerintah_kabupaten_sleman, R.string.pemerintah_kota_yogya, R.string.pemerintah_provinsi_diy)
            val pemda = pemdaId.map { stringResource(id = it) }
            Dropdown(
                pemda,
                label = stringResource(id = R.string.pilih_pemda),
                default = stringResource(id = R.string.pilih_pemda),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp)
            )
        }

        // Modal Disetor RUPS
        item{
            Text(text = stringResource(id = R.string.nominal_rbb_p), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(label = R.string.dalam_juta_rupiah, onValueChange = {}, modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
                focusManager = focusManager,
                keyboardType = KeyboardType.Number
            )
        }

        //Komposisi RUPS
        item{
            Text(text = stringResource(id = R.string.komposisi_rups), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(label = R.string.dalam_persen, onValueChange = {}, modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
                focusManager = focusManager,
                keyboardType = KeyboardType.Number
            )
        }
        // Realisasi Dana Setoran Modal
        item{
            Text(text = stringResource(id = R.string.realisasi_dana_setoran_modal), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
            TextInputBox(label = R.string.dalam_juta_rupiah, onValueChange = {}, modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
                focusManager = focusManager,
                keyboardType = KeyboardType.Number
            )
        }

        // Total Modal 31 Desember
        item{
            Text(text = stringResource(id = R.string.total_modal_31_desember), fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 10.dp))
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
                    val msg = stringResource(id = R.string.data_berhasil_ditambahkan)
                    Button(onClick = {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                    ) {
                        Text(text = stringResource(id = R.string.add))
                    }
                }
            }

        }

    }
}