package com.example.bpdmiscompose.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.R

@Composable
fun TopBarMenu (
    currentScreen : BPDMISScreen,
    onNavigationIconClick : () -> Unit = {},
    enableProfileButton : Boolean = false,
    onProfileButtonClick : () -> Unit = {},
    modifier : Modifier = Modifier,
){
    TopAppBar(
        modifier = modifier,
        title = { Text(stringResource(currentScreen.title)) },
        backgroundColor = colorResource(R.color.blue),
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.menu)
                )
            }
        },
        actions = {
            // Profile icon to go Back to Profile
            if(enableProfileButton){
                TopAppBarActionButton(
                    imageVector = Icons.Filled.AccountCircle,
                    onClick = onProfileButtonClick
                )
            }
        }
    )
}
