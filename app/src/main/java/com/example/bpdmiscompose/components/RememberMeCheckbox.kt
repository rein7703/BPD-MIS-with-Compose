package com.example.bpdmiscompose.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bpdmiscompose.R

// Checkboxes
@Composable
fun RememberMeCheckbox() {
    // in below line we are setting
    // the state of our checkbox.
    val checkedState = remember { mutableStateOf(true) }
    // in below line we are displaying a row
    // and we are creating a checkbox in a row.
    Row {
        Checkbox(
            // below line we are setting
            // the state of checkbox.
            checked = checkedState.value,
            // below line is use to add padding
            // to our checkbox.
            modifier = Modifier.padding(top = 3.dp, bottom = 3.dp),
            // below line is use to add on check
            // change to our checkbox.
            onCheckedChange = { checkedState.value = it },
            colors = CheckboxDefaults.colors(
                uncheckedColor = Color(0xFF0372D8),
                checkmarkColor = Color.White,
                checkedColor = Color(0xFF0372D8)
            )
        )
        // below line is use to add text to our check box and we are
        // adding padding to our text of checkbox
        Text(stringResource(R.string.remember_me), modifier = Modifier.padding(16.dp), color = Color.Black)
    }
}