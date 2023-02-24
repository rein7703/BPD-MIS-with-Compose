package com.example.bpdmiscompose

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bpdmiscompose.ViewModels.AuthViewModel
import com.example.bpdmiscompose.components.TextInputBoxPassword

@Composable
fun ChangePasswordScreen(viewModel : AuthViewModel,modifier: Modifier = Modifier, onClickNextButton : () -> Unit = {}){
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val user = viewModel.currentUser
    val oldPassword = remember{ mutableStateOf("") }
    val newPassword = remember{ mutableStateOf("") }
    val confirmPassword = remember{ mutableStateOf("") }
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
            TextInputBoxPassword(value = oldPassword.value, onValueChange = {oldPassword.value = it}, label = R.string.current_password, modifier = Modifier.fillMaxWidth(), focusManager = focusManager)
            TextInputBoxPassword(value = newPassword.value, onValueChange = {newPassword.value = it},label = R.string.new_password,modifier = Modifier.fillMaxWidth(), focusManager = focusManager )
            TextInputBoxPassword(value = confirmPassword.value, onValueChange = {confirmPassword.value = it}, label = R.string.confirm_new_password,modifier = Modifier.fillMaxWidth(), focusManager = focusManager)
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                Log.i(TAG, "ChangePasswordScreen: ${oldPassword.value} ${newPassword.value} ${confirmPassword.value}")
                try{
                    require(Regex(".{7,}").matches(newPassword.value)){"Password must be at least 6 characters"}
                    require(newPassword.value == confirmPassword.value){"Password doesn't match"}
                    viewModel.updatePassword(email = viewModel.currentUser?.email ?: "", oldPassword = oldPassword.value, newPassword = newPassword.value,
                    onComplete = {
                        if(it){
                            Toast.makeText(context, "Password has been changed", Toast.LENGTH_SHORT).show()
                            viewModel.logout()
                            onClickNextButton()
                        } else{
                            Toast.makeText(context, "Old password doesn't match", Toast.LENGTH_SHORT).show()
                        }
                    })
                }catch(e: Exception){
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }


            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0085FF), contentColor = Color.White), modifier = Modifier.fillMaxWidth())
            {
                Text(stringResource(R.string.change_password_header), color = Color.White)
            }

        }
    }
}