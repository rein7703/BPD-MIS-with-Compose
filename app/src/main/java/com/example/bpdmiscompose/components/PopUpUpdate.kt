package com.example.bpdmiscompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.example.bpdmiscompose.R

@Composable
fun PopUpUpdate(
    modifier :Modifier = Modifier,
    onButtonClicked : () -> Unit = {},
){
    val focusManager = LocalFocusManager.current
    Column (
        modifier = modifier
    ){
        IndikatorAddForm(
            focusManager = focusManager,
            buttonLabel = stringResource(id = R.string.update),
            onButtonClicked = { onButtonClicked },
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { focusManager.clearFocus() }
                    )
                }
                .background(color = Color(0xFFE9FCFF))
        )
    }

}