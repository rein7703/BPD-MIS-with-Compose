package com.example.bpdmiscompose.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.roboto

@Composable
fun TopBarBack(
    title : String,
    navigateUp : () -> Unit = {},
    modifier : Modifier = Modifier
){

    TopAppBar(
        title = { Text(
            text = title,
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start
        ) },
        modifier = Modifier,
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        },
        backgroundColor = Color(0xFF0372D8)
    )
}


