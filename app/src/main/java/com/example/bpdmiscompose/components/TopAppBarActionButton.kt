package com.example.bpdmiscompose.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.bpdmiscompose.R

@Composable
fun TopAppBarActionButton(
    imageVector: ImageVector,
    onClick : () -> Unit = {},
){
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = stringResource(R.string.bank_staff_profile)
        )
    }
}
