package com.example.bpdmiscompose.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SetoranModalTableREADONLY(
    pemda: List<String> = listOf("Pemda DIY", ""),
    tahun: List<String> = listOf("2022", ""),
    modalDisetorRUPS: List<String> = listOf("999999", ""),
    komposisiRUPS: List<String> = listOf("50", ""),
    realisasiDanaSetoranModal: List<String> = listOf("999999", ""),
    totalModalDesember: List<String> = listOf("999999", "")
) {
    LazyRow(modifier = Modifier.padding(20.dp)) {

        item() {
            Column {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(12.dp)
                ) {
                    Text(text = "Pemda\n", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
                pemda.forEach { it ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center

                        )
                    }
                }
            }
        }
        item {
            Column() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(text = "Tahun\n", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
                tahun.forEach { it ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(12.dp)
                    ) {
                        Text(text = it, fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
                    }
                }
            }
        }

        item {
            Column() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(text = "Modal Disetor RUPS\n(Juta Rp)", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
                modalDisetorRUPS.forEach { it ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(text = "Rp ${it}jt", fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
                    }
                }
            }
        }

        item {
            Column() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(text = "Komposisi RUPS \n(%)", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
                komposisiRUPS.forEach { it ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(text = "${it}%", fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
                    }
                }
            }
        }

        item {
            Column() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()

                        .padding(12.dp)
                ) {
                    Text(text = "Realisasi Dana Setoran Modal\n(Juta Rp)", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }

                realisasiDanaSetoranModal.forEach { it ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(12.dp)
                    ) {
                        Text(text = "Rp ${it}jt", fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
                    }
                }
            }
        }

        item {
            Column() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(text = "Total Modal Desember\n(Juta Rp)", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
                totalModalDesember.forEach { it ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(12.dp)
                    ) {
                        Text(text = "Rp ${it}jt", fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}