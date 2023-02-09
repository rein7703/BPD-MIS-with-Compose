package com.example.bpdmiscompose.screens


//import androidx.compose.foundation.layout.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.components.Dropdown


@Composable
fun AdminSkedulSetoranModalLayout (modifier: Modifier = Modifier, navController: NavHostController
) {
    LazyColumn (
        modifier = Modifier.padding(10.dp),
        ) {
        // Tahun
        item {
            Text(
                text = stringResource(id = R.string.tampilkan_tahun_spesifik),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 10.dp)
            )
            val years = (2017..2023).map { it.toString() }
            Dropdown(
                years,
                label = stringResource(id = R.string.pilih_tahun),
                default = stringResource(id = R.string.pilih_tahun),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp)
            )
            Row(
                modifier = Modifier.padding(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(onClick = { navController.navigate(BPDMISScreen.StaffSetoranBerdasarkanTahun.name) }) {
                        Text(
                            text = stringResource(id = R.string.tampilkan_berdasarkan_tahun),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)) { Divider() }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                    Text(
                        text = stringResource(
                            id = R.string.or
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Gray
                    )
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)) { Divider() }
            }
        }

        // Pemda
        item {
            Text(
                text = stringResource(id = R.string.tampilkan_pemda_spesifik),
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
            Row(
                modifier = Modifier.padding(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    /* EMPTY */
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(onClick = { navController.navigate(BPDMISScreen.StaffSetoranBerdasarkanPemda.name)}) {
                        Text(
                            text = stringResource(id = R.string.tampilkan_berdasarkan_pemda),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)) { Divider() }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                    Text(
                        text = stringResource(
                            id = R.string.or
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Gray
                    )
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)) { Divider() }
            }
        }
        // Tampilkan semua data
        item {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(onClick = {navController.navigate(BPDMISScreen.AdminSkedulCRUD.name)} , colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent, contentColor = MaterialTheme.colors.primary), border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary), modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(id = R.string.tampilkan_tambahkan_ubah_skedul), textAlign = TextAlign.Center)
            }
        }
    }
}
