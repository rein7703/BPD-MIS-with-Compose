package com.example.bpdmiscompose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bpdmiscompose.ButtonInfo
import com.example.bpdmiscompose.TextAndIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    icon : ImageVector = Icons.Filled.Person,
    name : String,
    jabatan : String,
    items : List<TextAndIcon> = emptyList(),
    buttons : List<ButtonInfo> = emptyList(),
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope : CoroutineScope,
    ){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon (
                imageVector = icon,
                contentDescription = "",
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = name, fontSize = 30.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colors.onPrimary)
            Text(text = jabatan, color = MaterialTheme.colors.onPrimary)
            Spacer(modifier = Modifier.height(40.dp))
            items.forEach{ item ->
                TextAndIconRow(item,
                    clickable = true,
                    color = MaterialTheme.colors.onPrimary,
                    onTextClick = {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                        navController.navigate(item.destination){
                            launchSingleTop = true
                        }
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ){
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                buttons.forEach{buttonInfo ->
                    if(buttonInfo.outlined){
                        OutlinedButton(onClick = buttonInfo.onButtonClick, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent, contentColor = buttonInfo.textColor), border = BorderStroke(width = 1.dp, color = Color.White), modifier = Modifier.fillMaxWidth()) {
                            Text(text = buttonInfo.text)
                        }
                    } else {
                        Button(onClick = buttonInfo.onButtonClick, colors = ButtonDefaults.buttonColors(backgroundColor = buttonInfo.backgroundColor, contentColor = buttonInfo.textColor), modifier = Modifier.fillMaxWidth()) {
                            Text(text = buttonInfo.text)
                        }
                    }
                }
            }
        }
    }
}