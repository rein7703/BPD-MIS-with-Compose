package com.example.bpdmiscompose.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import com.example.bpdmiscompose.R


@Composable
fun TextInputBoxEmail(value:String, onNewValue:(String) -> Unit, focusManager : FocusManager, modifier: Modifier = Modifier){
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = onNewValue,
        placeholder = {Text(stringResource(id = R.string.email))},
        keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()})
    )
}