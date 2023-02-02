package com.example.bpdmiscompose

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// ==========================================//
/* THIS SECTION IS FOR REUSABLE COMPONENTS */
// ==========================================//

// Text Boxes
@Composable
fun TextBox(
    @StringRes label: Int,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var usernameInput by remember { mutableStateOf("") }
    OutlinedTextField(
        value = usernameInput,
        onValueChange = {usernameInput = it},
        label = { Text(stringResource(label), fontFamily = roboto, fontWeight = FontWeight.Normal, color = Color(0xFF757575)) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor =  Color(0xFF757575), //hide the indicator
            unfocusedIndicatorColor = Color(0xFF757575),
            textColor = Color.Black
        ),
        modifier = Modifier.fillMaxWidth()

    )
}

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



//Navbars
@Composable
fun NavbarBack(header : String) {
    val contextForToast = LocalContext.current.applicationContext

    TopAppBar(
        title = { Text(text = header, fontFamily = roboto, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center, ) },
        navigationIcon = {
            IconButton(onClick = {
                Toast.makeText(
                    contextForToast,
                    "Login Icon Clicked",
                    Toast.LENGTH_SHORT
                ).show()
            })
            {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        backgroundColor = Color(0xFF0372D8)
    )
}