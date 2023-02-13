package com.example.bpdmiscompose.screens.adminscreens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.components.PopUpUpdate

@Composable
fun AdminIndikatorSearchResultLayout(
    navController: NavController,
){
    var popupControl by remember { mutableStateOf(false) }
    var context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize(5f)){
        if(popupControl){
            Popup(
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    excludeFromSystemGesture = false,
                ),
                onDismissRequest = { popupControl = false },
            ){
                PopUpUpdate(onButtonClicked = {
                    popupControl = false
                    Toast.makeText(context, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxSize(1f)
                )
            }
        }
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
                //Add Data
                Button(onClick = {navController.navigate(BPDMISScreen.AdminIndikatorAdd.name)}) {
                    Text(text = stringResource(id = R.string.add))
                }

                //Update Data
                Button(onClick = {
                    popupControl = true
                }
                ){
                    Text(text = stringResource(id = R.string.update))
                }
            }
        }
    }

}



