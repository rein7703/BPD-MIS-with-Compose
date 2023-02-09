package com.example.bpdmiscompose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.R

@Composable
fun AdminManajemenPenggunaLayout (modifier: Modifier = Modifier, navController:NavHostController){
    Column {
        Text(text = "Data Manajemen Pengguna akan ditampilkan disini")
        Row(
            modifier = Modifier.padding(20.dp)
        ){
            Column(modifier = Modifier
                .fillMaxSize()
                .weight(1f)){

            }
            Column(modifier = Modifier
                .fillMaxSize()
                .weight(1f),
                horizontalAlignment = Alignment.End) {
                Button(onClick = {navController.navigate(BPDMISScreen.AdminManajemenPenggunaAdd.name)}) {
                    Text(text = stringResource(id = R.string.add))
                }
            }
        }
    }
}