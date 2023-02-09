package com.example.bpdmiscompose

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bpdmiscompose.components.TextInputBoxPassword

@Preview(showBackground = true)
@Composable
fun ChangePasswordScreen(modifier: Modifier = Modifier, onClickNextButton : () -> Unit = {}){
    val focusManager = LocalFocusManager.current
    Column() {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(10.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                                    },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            Text(stringResource(R.string.change_password_header),fontFamily = roboto, fontWeight = FontWeight.Medium, color = Color.Black, textAlign = TextAlign.Center, fontSize = 36.sp, modifier = Modifier.fillMaxWidth() )
            Spacer(modifier = Modifier.height(15.dp))
            TextInputBoxPassword(label = R.string.current_password, modifier = Modifier.fillMaxWidth(), focusManager = focusManager)
            TextInputBoxPassword(label = R.string.new_password,modifier = Modifier.fillMaxWidth(), focusManager = focusManager )
            TextInputBoxPassword(label = R.string.confirm_new_password,modifier = Modifier.fillMaxWidth(), focusManager = focusManager)
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = onClickNextButton, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0085FF), contentColor = Color.White), modifier = Modifier.fillMaxWidth())
            {
                Text(stringResource(R.string.change_password_header), color = Color.White)
            }

        }
    }
}