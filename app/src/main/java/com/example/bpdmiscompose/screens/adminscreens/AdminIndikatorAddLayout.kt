package com.example.bpdmiscompose.screens.adminscreens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.components.IndikatorAddForm


@Preview(showBackground = true)
@Composable
fun AdminIndikatorAddLayout(){
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    IndikatorAddForm(
        focusManager = focusManager,
        onButtonClicked = { Toast.makeText(context, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show() },
        buttonLabel = stringResource(
        id = R.string.add
    ))
}