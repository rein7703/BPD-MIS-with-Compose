package com.example.bpdmiscompose


import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bpdmiscompose.ViewModels.AuthViewModel
import com.example.bpdmiscompose.ViewModels.StaffSetoranModalViewModel
import com.example.bpdmiscompose.components.Dropdown
import com.example.bpdmiscompose.components.SetoranModalTableREADONLY
import com.example.bpdmiscompose.repositories.Resources


@Composable
fun StaffSetoranModalLayout (
    authViewModel: AuthViewModel? = null,
    modifier: Modifier=Modifier
) {
    val staffSetoranModalViewModel: StaffSetoranModalViewModel = hiltViewModel()
    val staffSetoranModalUiState = staffSetoranModalViewModel.staffSetoranModalUiState
    val context = LocalContext.current
    val yearsUnsorted = staffSetoranModalUiState.yearList.data?.map { it.toString() }
    val drawerItems =
        listOf("Lihat Semua Data").plus(yearsUnsorted?.sortedDescending()
            ?: emptyList()
        )
    var selectedItem by remember { mutableStateOf<String>(drawerItems[0]) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Dropdown(
                drawerItems,
                label = stringResource(R.string.tahun_setoran_modal),
                default = drawerItems[0],
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.TopStart)
                    .padding(20.dp),
                onItemSelected = {
                    selectedItem = it
                    Log.i(TAG, "the selected item is ${selectedItem.toString()}")
                }
            )
        }

        item {
            when (staffSetoranModalUiState.setoranList) {
                is Resources.Loading -> CircularProgressIndicator()
                is Resources.Success -> {
                    if (selectedItem == drawerItems[0]) {
                        staffSetoranModalViewModel.loadAll()
                    } else {
                        staffSetoranModalViewModel.loadByYear(selectedItem!!.toInt())
                    }
                }
                else -> {
                    Log.e(
                        TAG,
                        staffSetoranModalUiState.setoranList.throwable?.localizedMessage.toString()
                    );
                    Toast.makeText(
                        context,
                        staffSetoranModalUiState.setoranList.throwable?.localizedMessage
                            ?: "Unknown Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        item {
            val pemda =
                staffSetoranModalUiState.setoranList.data?.map { it.pemdaId.toString() }
                    ?: listOf()
            val tahun =
                staffSetoranModalUiState.setoranList.data?.map { it.tahun.toString() }
                    ?: listOf()
            val modalDisetorRUPS =
                staffSetoranModalUiState.setoranList.data?.map { it.modalDisetorRUPS.toString() }
                    ?: listOf()
            val komposisiRUPS =
                staffSetoranModalUiState.setoranList.data?.map { it.komposisiRUPS.toString() }
                    ?: listOf()
            val realisasiDanaSetoranModal =
                staffSetoranModalUiState.setoranList.data?.map { it.realisasiDanaSetoranModal.toString() }
                    ?: listOf()
            val totalModalDesember =
                staffSetoranModalUiState.setoranList.data?.map { it.totalModalDesember.toString() }
                    ?: listOf()
            SetoranModalTableREADONLY(
                pemda,
                tahun,
                modalDisetorRUPS,
                komposisiRUPS,
                realisasiDanaSetoranModal,
                totalModalDesember
            )
        }

    }
}










