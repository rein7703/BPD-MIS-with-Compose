package com.example.bpdmiscompose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bpdmiscompose.TextAndIcon
import com.example.bpdmiscompose.R


@Composable
fun TextAndIconRow (
    textAndIcon : TextAndIcon,
    color : Color,
    clickable : Boolean = false,
    onTextClick : () -> Unit = {},
    modifier: Modifier = Modifier
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = clickable, onClick = onTextClick)
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

        Icon(
            imageVector = textAndIcon.icon,
            contentDescription = stringResource(R.string.jabatan),
            tint = color
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = textAndIcon.text, color = color)
    }
}