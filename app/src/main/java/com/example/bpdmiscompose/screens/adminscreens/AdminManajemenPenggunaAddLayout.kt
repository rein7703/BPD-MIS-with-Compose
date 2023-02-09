package com.example.bpdmiscompose.screens.adminscreens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.components.Dropdown
import com.example.bpdmiscompose.components.RadioButtonSample
import com.example.bpdmiscompose.components.TextInputBox

@Composable
fun AdminManajemenPenggunaAddLayout() {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .padding(20.dp)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ) {
        // Nama Cabang
        item {
            Text(
                text = stringResource(id = R.string.nama),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 10.dp)
            )
            TextInputBox(
                label = R.string.nama_lengkap, onValueChange = {}, modifier = Modifier
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
                stringResource(id = R.string.jabatan),
                default = stringResource(id = R.string.jabatan),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
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
            RadioButtonSample(aktif)
        }

        //Button
        item {
            Row() {
                Column(modifier = Modifier.weight(1f)) {
                }
                Column(modifier = Modifier.weight(1f)) {
                    val msg = stringResource(id = R.string.data_berhasil_ditambahkan)
                    Button(
                        onClick = {
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
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