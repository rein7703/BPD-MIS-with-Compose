package com.example.bpdmiscompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChangePasswordScreen(modifier: Modifier = Modifier, onNextButtonClicked : () -> Unit = {}){
    Column() {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            Text(stringResource(R.string.change_password_header),fontFamily = roboto, fontWeight = FontWeight.Medium, color = Color.Black, textAlign = TextAlign.Center, fontSize = 36.sp, modifier = Modifier.fillMaxWidth() )
            Spacer(modifier = Modifier.height(15.dp))
            TextBox(label = R.string.current_password)
            Spacer(modifier = Modifier.height(5.dp))
            TextBox(label = R.string.new_password)
            Spacer(modifier = Modifier.height(5.dp))
            TextBox(label = R.string.confirm_new_password)
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = onNextButtonClicked, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0085FF), contentColor = Color.White), modifier = Modifier.fillMaxWidth())
            {
                Text(stringResource(R.string.change_password_header), color = Color.White)
            }

        }
    }
}