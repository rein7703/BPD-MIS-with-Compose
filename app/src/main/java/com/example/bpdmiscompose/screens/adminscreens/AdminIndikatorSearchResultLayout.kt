package com.example.bpdmiscompose.screens.adminscreens

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
import androidx.navigation.NavController
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.R

@Composable
fun AdminIndikatorSearchResultLayout(
    navController: NavController,
){
    Column(){
        Text(text = "HASIL SEARCH INDIKATOR")
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
                Button(onClick = {navController.navigate(BPDMISScreen.AdminIndikatorAdd.name)}) {
                    Text(text = stringResource(id = R.string.add))
                }
            }
        }
    }

}

