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
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bpdmiscompose.ViewModels.AuthViewModel
import com.example.bpdmiscompose.components.TextInputBoxEmail
import com.example.bpdmiscompose.components.TextInputBoxPassword
import com.example.bpdmiscompose.components.TopBarBack
import com.example.bpdmiscompose.repositories.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel : AuthViewModel,
    modifier : Modifier = Modifier,
    onStaffButtonClicked: () -> Unit = {},
    onPemdaButtonClicked :() -> Unit = {},
    onAdminButtonClicked : () -> Unit = {},
){
    var page by remember{mutableStateOf(0)} // 0 = staff , 1 = pemda, 2 = admin
    var loadingState by remember {mutableStateOf(false)}
    var toastDisplayed by remember{mutableStateOf(false)}
    val focusManager = LocalFocusManager.current
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    val loginFlow = viewModel.loginFlow.collectAsState()

    Scaffold(
        topBar = { TopBarBack(title = stringResource(id = R.string.login), navigateUp = {navController.popBackStack()})},
    ) {
        Column(modifier = Modifier.background(Color.White).fillMaxWidth().fillMaxHeight().padding(10.dp).pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start
        ) {
            val context = LocalContext.current
            loginFlow.value?.let{
                when(it){
                    is Resource.Failure -> {
                        loadingState = false
                        if (!toastDisplayed) {
                            Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                            toastDisplayed = true
                        }
                    }
                    is Resource.Loading -> loadingState = true
                    is Resource.Success -> {
                        loadingState = false
                        LaunchedEffect(Unit){
                            val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
                            if(user != null){
                                user.getIdToken(true).addOnCompleteListener{task ->
                                    if(task.isSuccessful){
                                        val tokenResult: GetTokenResult? = task.result
                                        if(tokenResult != null){
                                            val customClaims: Map<String, Any>? = tokenResult.claims
                                            if(customClaims != null){
                                               if(customClaims["isAdmin"] as Boolean) {onAdminButtonClicked()}
                                                else if(customClaims["isKomisaris"] as Boolean || customClaims["isDireksi"] as Boolean || customClaims["isDivisi"] as Boolean){onStaffButtonClicked()}
                                                else if(customClaims["isPemda"] as Boolean){onPemdaButtonClicked()}
                                            }else {
                                                onPemdaButtonClicked()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(context, "IS NULL", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Image(painter = painterResource(id = R.drawable.logo_bank_bpd_diy_clr), contentDescription = "Logo BPDMIS", modifier = Modifier.fillMaxWidth().height(200.dp).padding(bottom = 50.dp))
            TextInputBoxEmail(value = email, onNewValue = { email = it }, modifier = Modifier.fillMaxWidth(), focusManager = focusManager)
            Spacer(modifier = Modifier.height(8.dp))
            TextInputBoxPassword(value = password, label = R.string.prompt_password, modifier = Modifier.fillMaxWidth(), onValueChange = { password = it }, focusManager = focusManager)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    page = 0
                    toastDisplayed = false
                    viewModel.login(email, password)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF0085FF),
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                if(loadingState){
                    CircularProgressIndicator(modifier = Modifier.align(CenterVertically))
                }else Text(stringResource(R.string.login))
            }
        }
    }
}


