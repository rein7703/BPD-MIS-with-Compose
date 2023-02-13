package com.example.bpdmiscompose

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bpdmiscompose.components.RememberMeCheckbox
import com.example.bpdmiscompose.components.TextInputBoxEmail
import com.example.bpdmiscompose.components.TextInputBoxPassword
import com.example.bpdmiscompose.components.TopBarBack
import com.example.bpdmiscompose.data.Resource
import com.example.bpdmiscompose.models.AuthViewModel



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    viewModel : AuthViewModel,
    modifier : Modifier = Modifier,
    onStaffButtonClicked: () -> Unit = {},
    onPemdaButtonClicked :() -> Unit = {},
    onAdminButtonClicked : () -> Unit = {},
    //anotherViewModel: LoginViewModel = hiltViewModel()
){
    val focusManager = LocalFocusManager.current
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    val loginFlow = viewModel?.loginFlow?.collectAsState()

    Scaffold(
        topBar = { TopBarBack(title = stringResource(id = R.string.login))},
    ){
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
            TextInputBoxEmail(value = email, onNewValue = {email = it}, modifier = Modifier.fillMaxWidth(), focusManager = focusManager)
            Spacer(modifier = Modifier.height(8.dp))
            TextInputBoxPassword(value = password, label = R.string.prompt_password, modifier = Modifier.fillMaxWidth(), onValueChange = {password = it}, focusManager = focusManager)
            RememberMeCheckbox()
            Button(onClick = { viewModel?.login(email,password) }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0085FF), contentColor = Color.White), modifier = Modifier.fillMaxWidth()
            ){
                Text(stringResource(R.string.login))
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .padding(10.dp))
                {
                    Button(onClick = onPemdaButtonClicked){
                        Text(stringResource(R.string.review_pemda_button))
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .padding(10.dp)

                ) {
                    Button(onClick = onAdminButtonClicked) {
                        Text(stringResource(R.string.review_admin_button))
                    }
                }
            }
            loginFlow?.value?.let{
                when(it){
                    is Resource.Failure -> {
                        val context = LocalContext.current
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                    }
                    Resource.Loading -> {
                        CircularProgressIndicator(modifier = Modifier, Color.Gray, 10.dp)
                    }
                    is Resource.Success -> {
                        LaunchedEffect(Unit){
                            onStaffButtonClicked
                        }
                    }
                }
            }
        }

    }
}



