package com.example.bpdmiscompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize


@Preview
@Composable
fun Dropdown(
    items : List<String> = emptyList(),
    label : String ="",
    default : String = "",
    modifier : Modifier = Modifier,
    color: Color = Color.Transparent,
    onItemSelected : (String) -> Unit = {}
){
    var selectedItem by remember { mutableStateOf(default) }
    var expandedState by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expandedState)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Column(

    ){
        Box(modifier = modifier
        ){
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        // This value is used to assign to
                        // the DropDown the same width
                        textFieldSize = coordinates.size.toSize()
                    }
                    .background(color = color)
                    .clickable {expandedState = !expandedState},
                label = { Text(label, color = MaterialTheme.colors.onSurface, fontSize = 12.sp) },
                trailingIcon = {
                    Icon(icon,"contentDescription",
                        Modifier.clickable { expandedState = !expandedState })
                },
                enabled = false,
                textStyle = TextStyle(
                    color = if (selectedItem == default) Color.Gray else MaterialTheme.colors.onSurface
                )
            )
            DropdownMenu(
                expanded = expandedState,
                onDismissRequest = {expandedState = false},
                modifier = Modifier.width(with(LocalDensity.current){textFieldSize.width.toDp()})
            ){
                items.forEach{item ->
                    DropdownMenuItem(
                        onClick = {
                            expandedState = false
                            selectedItem = item
                            onItemSelected(item)
                        }
                    ) {
                        Text(text = item, color = MaterialTheme.colors.onSurface)
                    }
                }
            }
        }
    }
}