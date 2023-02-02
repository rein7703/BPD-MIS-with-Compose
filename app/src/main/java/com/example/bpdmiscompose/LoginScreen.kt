package com.example.bpdmiscompose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(modifier : Modifier = Modifier, onNextButtonClicked: () -> Unit = {}){
    Scaffold(
        topBar = {/*NavbarBack("Login")*/},
        content = {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(10.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {

                Text(stringResource(R.string.login_msg), fontFamily = roboto, fontWeight = FontWeight.Medium, color = Color.Black, textAlign = TextAlign.Center, fontSize = 36.sp)
                TextBox(label = R.string.prompt_username, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                TextBox(label = R.string.prompt_password, modifier = Modifier.fillMaxWidth())
                RememberMeCheckbox()
                Button(onClick = onNextButtonClicked, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0085FF), contentColor = Color.White), modifier = Modifier.fillMaxWidth()
                ){
                    Text(stringResource(R.string.login), fontFamily = roboto, fontWeight = FontWeight.Medium)
                }

            }
        }
    )

}