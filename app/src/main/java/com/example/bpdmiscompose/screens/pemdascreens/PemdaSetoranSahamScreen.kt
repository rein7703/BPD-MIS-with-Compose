package com.example.bpdmiscompose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.components.Dropdown
import com.example.bpdmiscompose.roboto

@Preview(showBackground = true)
@Composable
fun PemdaSetoranSahamScreen(
    nama : String = "nama",
){

    Column(
        modifier = Modifier.padding(15.dp)
    ){
        Text(text = "Selamat Datang, ${nama}!", fontFamily = roboto, fontWeight = FontWeight.Medium, color = Color.Black, textAlign = TextAlign.Start, fontSize = 36.sp)
        val items = (2017..2023).map{it.toString()}
        Dropdown(items,
            label = stringResource(R.string.tahun_setoran_modal),
            default = stringResource(R.string.pilih_tahun),
            modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .padding(20.dp)
        )
    }

}