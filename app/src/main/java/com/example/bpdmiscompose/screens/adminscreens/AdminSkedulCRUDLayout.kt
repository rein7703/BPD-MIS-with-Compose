package com.example.bpdmiscompose.screens.adminscreens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.ViewModels.SkedulSetoranViewModel
import com.example.bpdmiscompose.components.SkedulSetoranTableEditable

@Composable
fun AdminSkedulCRUDLayout(modifier:Modifier = Modifier, navController : NavHostController, skedulSetoranViewModel: SkedulSetoranViewModel
){
    val context = LocalContext.current
    val skedulSetoranUiState = skedulSetoranViewModel.skedulSetoranUiState


    LazyColumn(modifier = Modifier.padding(20.dp)){
        //title
        item{Text(text = "Admin Skedul CRUD Layout", fontWeight = FontWeight.Bold, fontSize = 30.sp)}

        //Table
        item{
            SkedulSetoranTableEditable(
                skedulSetoranList = skedulSetoranUiState.setoranList.data ?: listOf(),
                onDelete = {skedulSetoranViewModel.deleteSkedulSetoran(it, onComplete = {Toast.makeText(context, "Successfully Delete", Toast.LENGTH_SHORT).show()})},
                onSetAddedPemegangSaham = {skedulSetoranViewModel.setPemegangSahamChosen(it)},
                onSetAddedTahun = {skedulSetoranViewModel.setYearChosen(it)},
                onSetSkedulId = {skedulSetoranViewModel.setSkedulIdChosen(it)},
                onNavigateToAddPage = {navController.navigate(BPDMISScreen.AdminSkedulAdd.name)},
                onUpdate = {navController.navigate(BPDMISScreen.AdminSkedulUpdate.name)}
            )
        }

        // button
        item{
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.Bottom
            ){
            Column(modifier = Modifier
                .fillMaxSize()
                .weight(1f)){

            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalAlignment = Alignment.End
                ) {
                Button(onClick = {navController.navigate(BPDMISScreen.AdminSkedulAdd.name)}) {
                    Text(text = stringResource(id = R.string.add))
                }
            }
            }
        }
    }
}



