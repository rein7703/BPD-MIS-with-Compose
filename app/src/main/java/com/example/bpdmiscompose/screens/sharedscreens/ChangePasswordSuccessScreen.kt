package com.example.bpdmiscompose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChangePasswordSuccessScreen(modifier: Modifier = Modifier, onClickCancelButton : () -> Unit = {}){
    // For the Success Message, Will be at the center of the screen
    Column(
        modifier = Modifier
            .background(colorResource(R.color.dark_blue))
            .padding(10.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(painter = painterResource(R.drawable.icons8_confetti_96), contentDescription = "Conffetti", modifier = Modifier.size(110.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Text(stringResource(R.string.selamat), color = Color.White, textAlign = TextAlign.Center, fontSize = 20.sp, fontFamily = roboto, fontWeight = FontWeight.Bold)
        Text(stringResource(R.string.selamat_msg), color = Color.White, textAlign = TextAlign.Center, fontSize = 20.sp, fontFamily = roboto, fontWeight = FontWeight.Normal)
    }
    // For the Button, will be at the bottom
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom
    ) {
        OutlinedButton(onClick = onClickCancelButton, colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent, contentColor = Color.White ), border = BorderStroke(1.dp, Color.White), modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp) ) {
            Text(stringResource(R.string.kembali), fontFamily = roboto, fontWeight = FontWeight.Medium)

        }
    }

}
