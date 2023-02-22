package com.example.bpdmiscompose

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bpdmiscompose.ViewModels.SkedulSetoranViewModel
import com.example.bpdmiscompose.components.Dropdown
import com.example.bpdmiscompose.repositories.Resources


@Composable
fun StaffSkedulSetoranModalLayout (modifier: Modifier=Modifier, navController: NavHostController, skedulSetoranViewModel: SkedulSetoranViewModel) {
    val context = LocalContext.current
    val skedulSetoranUiState = skedulSetoranViewModel.skedulSetoranUiState
    val years = skedulSetoranUiState.yearsList.data?.map {it} ?: emptyList()
    val dropdownItems = years.sortedByDescending { it }.map{it.toString()}
    var selectedItem = remember { mutableStateOf("") }
    val pageSelected = remember { mutableStateOf(0) } // 0 = Tahun, 1 = Pemda, 2 = all data
    val hasRedirected = remember{ mutableStateOf(true) }
    val circularState = remember{ mutableStateOf(false) }

    when(skedulSetoranUiState.setoranList){
        is Resources.Loading -> circularState.value = true
        is Resources.Success -> {
            circularState.value = false
            if(!hasRedirected.value){
                when(pageSelected.value){
                    0 -> {
                        navController.navigate(BPDMISScreen.StaffSkedulBerdasarkanTahun.name)
                        hasRedirected.value = true
                    }
                    1 -> {
                        navController.navigate(BPDMISScreen.StaffSkedulBerdasarkanPemda.name)
                        hasRedirected.value = true
                    }
                    2 -> {
                        navController.navigate(BPDMISScreen.StaffSemuaSkedul.name)
                        hasRedirected.value = true
                    }
                }
            }
        }
        is Resources.Error -> {
            circularState.value = false
            Log.e(ContentValues.TAG, skedulSetoranUiState.setoranList.throwable?.message.toString())
            Toast.makeText(context, skedulSetoranUiState.setoranList.throwable?.message, Toast.LENGTH_SHORT).show()
        }
    }

    LazyColumn (
        modifier = Modifier.padding(10.dp),
    ) {


        // Label Tahun : Tampilkan Tahun Spesifik
        item {
            Text(
                text = stringResource(id = R.string.tampilkan_tahun_spesifik),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }


        // Dropdown Tahun
        item {
            Dropdown(
                dropdownItems,
                label = stringResource(R.string.tahun_setoran_modal),
                default = stringResource(R.string.pilih_tahun),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp),
                onItemSelected = {
                    selectedItem.value = it
                    Log.i(ContentValues.TAG, "the selected item is ${selectedItem.toString()}")
                }
            )
        }

        // Button Tampilkan
        item {
            Row(
                modifier = Modifier.padding(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    //Leave this empty
                    //This part is to ensure that the button is aligned to the right
                }

                // This is the column where the button resides
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(onClick = {
                        try{
                            Log.i(ContentValues.TAG, "the selected item is ${selectedItem.toString()}")
                            hasRedirected.value = false
                            pageSelected.value = 0
                            skedulSetoranViewModel.getSkeduSetoranByTahun(selectedItem.value.toInt())


                        } catch(e: Exception){
                            Toast.makeText(context, "Mohon pilih tahun yang valid sebelum melihat data", Toast.LENGTH_SHORT).show()
                        }

                    }) {
                        Text(
                            text = stringResource(id = R.string.tampilkan_berdasarkan_tahun),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Divider
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f)
                ) { Divider() }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.or
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Gray
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f)
                ) { Divider() }
            }
        }

        // Pemda
        item {
            Text(
                text = stringResource(id = R.string.tampilkan_pemda_spesifik),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 10.dp)
            )
            val pemegangSahamList = skedulSetoranUiState.pemegangSahamList.data?.map {it} ?: emptyList()
            val pemegangSahamDropdownItems = pemegangSahamList.sortedByDescending { it }.map{it.toString()}
            var pemegangSahamSelectedItem = remember { mutableStateOf("") }

            Dropdown(
                pemegangSahamDropdownItems,
                label = stringResource(id = R.string.pilih_pemda),
                default =  stringResource(id = R.string.pilih_pemda),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp),
                onItemSelected = {
                    pemegangSahamSelectedItem.value = it
                    Log.i(ContentValues.TAG, "the selected item is ${selectedItem.toString()}")
                }
            )
            Row(
                modifier = Modifier.padding(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    //Leave this empty
                    //This part is to ensure that the button is aligned to the right
                }

                // This column is where the button resides
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(onClick = {
                        try{
                            require(pemegangSahamSelectedItem.value.isNotEmpty()){"Mohon pilih pemegang saham yang valid sebelum melihat data"}
                            Log.i(ContentValues.TAG, "the selected pemegang saham is ${pemegangSahamSelectedItem.value}")
                            hasRedirected.value = false
                            pageSelected.value = 1
                            skedulSetoranViewModel.getSkedulSetoranByPemegangSaham(pemegangSahamSelectedItem.value)
                        } catch(e: Exception) {
                            Log.e(ContentValues.TAG, e.message.toString())
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text(
                            text = stringResource(id = R.string.tampilkan_berdasarkan_pemda),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        )
                    }
                }
            }


            // This is a divider
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f)
                ) { Divider() }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.or
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Gray
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f)
                ) { Divider() }
            }
        }









        // Tampilkan semua data
        item {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(onClick = {
                hasRedirected.value = false
                pageSelected.value = 2
                skedulSetoranViewModel.getSkedulSetoran()


            },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = MaterialTheme.colors.primary
                ),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary),
                modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.tampilkan_semua_data),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}



