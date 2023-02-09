package com.example.bpdmiscompose


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bpdmiscompose.components.Dropdown

@Preview(showBackground = true)
@Composable
fun StaffSetoranModalLayout (modifier: Modifier=Modifier){
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

// make a list of 2017 to 2023 in descending order and map it to string
// then pass it to Dropdown composable
// then pass the label and default value to Dropdown composable
// then pass the modifier to Dropdown composable




