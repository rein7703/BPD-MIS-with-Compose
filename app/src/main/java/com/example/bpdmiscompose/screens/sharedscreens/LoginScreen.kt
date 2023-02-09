package com.example.bpdmiscompose

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bpdmiscompose.components.RememberMeCheckbox
import com.example.bpdmiscompose.components.TextInputBox
import com.example.bpdmiscompose.components.TextInputBoxPassword

@Preview(showBackground = true)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(modifier : Modifier = Modifier, onStaffButtonClicked: () -> Unit = {}, onPemdaButtonClicked :() -> Unit = {}, onAdminButtonClicked : () -> Unit = {}){
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {/*NavbarBack("Login")*/},
        content = {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp)
                    .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_bpd_color_adjusted),
                    contentDescription = "Logo BPDMIS",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 50.dp)
                )
                TextInputBox(label = R.string.prompt_username, modifier = Modifier.fillMaxWidth(), focusManager = focusManager)
                Spacer(modifier = Modifier.height(8.dp))
                TextInputBoxPassword(label = R.string.prompt_password, modifier = Modifier.fillMaxWidth(), focusManager = focusManager)
                RememberMeCheckbox()
                Button(onClick = onStaffButtonClicked, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0085FF), contentColor = Color.White), modifier = Modifier.fillMaxWidth()
                ){
                    Text(stringResource(R.string.login))
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column(
                        modifier = Modifier.fillMaxWidth().weight(0.5f).padding(10.dp)
                    ) {
                        Button(onClick = onPemdaButtonClicked){
                            Text(stringResource(R.string.review_pemda_button))
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth().weight(0.5f).padding(10.dp)

                    ) {
                        Button(onClick = onAdminButtonClicked) {
                            Text(stringResource(R.string.review_admin_button))
                        }
                    }
                }


            }
        }
    )

}