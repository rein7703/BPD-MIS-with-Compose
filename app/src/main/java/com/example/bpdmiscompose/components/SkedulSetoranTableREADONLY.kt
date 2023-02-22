package com.example.bpdmiscompose.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bpdmiscompose.dataClass.SkedulSetoranModal


@Preview(showBackground = true)
@Composable
fun SkedulSetoranTableREADONLY(skedulSetoranList: List<SkedulSetoranModal> = exampleSkedulSetoranList) {
    Log.i(TAG, "START!!!")
    val tahunUnsorted= skedulSetoranList.map{it.tahun.toString()}.distinct() ?: emptyList()
    val tahunList = tahunUnsorted.sortedDescending()
    val pemdaList = skedulSetoranList.map{it.pemegangSaham}.distinct()
    val skedList = mutableListOf<SkedulClassForEditableTable>()
    pemdaList.forEach{pemda->
        val groupList = mutableListOf<List<String>>()
        skedulSetoranList.forEach{skedulSetoranModal ->
            if(skedulSetoranModal.pemegangSaham == pemda){
                groupList.add(listOf(
                    skedulSetoranModal.tahun.toString(),
                    skedulSetoranModal.nominal.toString(),
                    skedulSetoranModal.skedulId.toString()
                ))
            }
        }
        skedList.add(SkedulClassForEditableTable(pemda, groupList))
    }
    Log.i(TAG, "${skedList.toString()}")

    LazyRow() {

        item() {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Pemegang Saham",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(40.dp))
                pemdaList.forEach() {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Text(text = "Total Modal",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }

        items(tahunList){tahun ->
            var count = 0
            var totalCapitalPerYear = 0
            Log.i(TAG, "Tahun yang ada adalah $tahunList")
            Log.i(TAG, "Tahun yang dipilih adalah $tahun")
            Column(modifier = Modifier.padding(20.dp)){
                Text(
                    text = tahun,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(40.dp))
                pemdaList.forEach{pemda ->
                    val isThereData = remember{ mutableStateOf(false) }
                    skedList.forEach {  sked->
                        count++
                        Log.i(TAG, "This is row ${sked.toString()}")
                        if (sked.pemegangSaham == pemda){
                            sked.ListTahunNominal.forEach{it ->
                                if(it[0] == tahun){
                                    Text(
                                        text = "Rp ${it[1]}juta",
                                        fontWeight = FontWeight.Normal,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .clickable(onClick = {Log.i(TAG, "You are clicking year ${it[0]} nominal ${it[1]} with id ${it[2]}")})
                                    )
                                    isThereData.value = true
                                    totalCapitalPerYear += it[1].toInt()
                                }
                            }
                        }
                    }
                    if(!isThereData.value){
                        Text(
                            text = "EMPTY",
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
                Text(text = "Rp ${totalCapitalPerYear.toString()}juta", textAlign = TextAlign.Center, modifier = Modifier.padding(10.dp))
            }
        }
    }




}
