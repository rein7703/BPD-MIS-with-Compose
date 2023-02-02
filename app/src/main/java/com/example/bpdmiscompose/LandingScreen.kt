package com.example.bpdmiscompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LandingScreen(modifier : Modifier = Modifier, onNextButtonClicked: () -> Unit = {}, ) {
    /*val montserrat = FontFamily(
        Font(R.font.montserrat_light, FontWeight.Light),
        Font(R.font.montserrat_medium, FontWeight.Medium),
        Font(R.font.montserrat_regular, FontWeight.Normal),
        Font(R.font.montserrat_bold, FontWeight.Bold),
        Font(R.font.montserrat_black, FontWeight.Black)
    ) */
    Column(
        modifier = Modifier
            .background(Color(0xFF353890))
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ){
        Image(painter = painterResource(R.drawable.logo_bpd), contentDescription = "logo")
        Spacer(modifier = Modifier.height(150.dp))
        Text(stringResource(R.string.welcome), color = Color.White, textAlign = TextAlign.Center, fontSize = 20.sp, fontFamily = roboto, fontWeight = FontWeight.Medium)
        Text(stringResource(R.string.welcome_msg), color = Color.White, textAlign = TextAlign.Center, fontSize = 15.sp, fontFamily = roboto, fontWeight = FontWeight.Normal )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onNextButtonClicked, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0085FF), contentColor = Color.White), modifier = Modifier
            .fillMaxWidth()
            .padding(start = 3.dp, end = 3.dp)) {

            Text(stringResource(R.string.login), fontFamily = roboto, fontWeight = FontWeight.Medium)

        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}