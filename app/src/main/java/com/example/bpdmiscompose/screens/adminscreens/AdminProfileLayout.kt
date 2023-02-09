package com.example.bpdmiscompose.screens.adminscreens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.TextAndIcon
import com.example.bpdmiscompose.components.ProfileHeaderRow
import com.example.bpdmiscompose.components.TextAndIconRow
@Composable
fun AdminProfileLayout(
    adminName : String,
    adminJabatan : String,
    adminId : String,
    adminAddress : String,
    adminEmail : String,
    adminPhoneNumber : String,
    modifier : Modifier = Modifier

){
    Column(
        modifier = Modifier.fillMaxHeight()
    ){
        // For the top part including name, account circle, and line divider
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            ProfileHeaderRow(icon = Icons.Filled.AccountCircle, name = adminName)
            Spacer(modifier = Modifier.height(2.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Text(stringResource(R.string.identitas), fontSize = 20.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colors.onSurface)
        }

        // For the bottom part of the profile including jabatan, alamat, email, and phoneNumber
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ){
            val identitasList = listOf<TextAndIcon>(
                TextAndIcon(text = adminJabatan, icon = Icons.Filled.Person),
                TextAndIcon(text = adminAddress, icon = Icons.Filled.Home),
                TextAndIcon(text = adminId, icon = Icons.Filled.Badge),
                TextAndIcon(text = adminEmail , icon = Icons.Filled.Email),
                TextAndIcon(text = adminPhoneNumber, icon = Icons.Filled.Phone)
            )
            identitasList.forEach{identitas ->
                TextAndIconRow(textAndIcon = identitas, color = MaterialTheme.colors.onSurface)
            }
        }
    }


}