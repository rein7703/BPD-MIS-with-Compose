package com.example.bpdmiscompose.screens

import androidx.compose.foundation.layout.*
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
import com.example.bpdmiscompose.components.Dropdown

@Composable
fun AdminSetoranModalLayout (modifier: Modifier = Modifier, navController: NavHostController){
    Column(
        modifier = Modifier.padding(5.dp)
    ){
        val items = (2017..2023).map{it.toString()}
        Dropdown(items, label = stringResource(R.string.tahun_setoran_modal), default = stringResource(R.string.pilih_tahun), modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
            .padding(20.dp)
        )
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
                    navController.navigate(BPDMISScreen.AdminSetoranAdd.name)
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