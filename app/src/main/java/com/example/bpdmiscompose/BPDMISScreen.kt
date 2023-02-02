package com.example.bpdmiscompose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


val roboto = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_light, FontWeight.Light)
)

enum class BPDMISScreen(@StringRes val title:Int){
    Landing(title = R.string.app_name),
    Login(title = R.string.login),
    ChangePassword(title = R.string.change_password_header),
    ChangePasswordSuccess(title = R.string.selamat)
}

@Preview(showBackground = true)
@Composable
fun BPDMISApp(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = BPDMISScreen.valueOf(
        backStackEntry?.destination?.route ?: BPDMISScreen.Landing.name
    )

    Scaffold (
        topBar = {
            if (navController.previousBackStackEntry == null){

            }else{
                BPDMISBackBar(
                currentScreen = currentScreen,
                navigateUp = { navController.navigateUp() }
                )
            }
        }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BPDMISScreen.Landing.name,
            modifier = Modifier.padding(innerPadding)

        ){
            composable(route = BPDMISScreen.Landing.name){
                LandingScreen(
                    onNextButtonClicked = {
                        navController.navigate(BPDMISScreen.Login.name)
                    }
                )
            }
            composable(route = BPDMISScreen.Login.name){
                LoginScreen(
                    onNextButtonClicked = {
                        navController.navigate(BPDMISScreen.ChangePassword.name)
                    }
                )
            }
            composable (route = BPDMISScreen.ChangePassword.name){
                ChangePasswordScreen(
                    onNextButtonClicked = {
                        navController.navigate(BPDMISScreen.ChangePasswordSuccess.name){
                            popUpTo(BPDMISScreen.Landing.name){inclusive = true}
                        }
                    }
                )
            }
            composable(route = BPDMISScreen.ChangePasswordSuccess.name){
                ChangePasswordSuccessScreen(
                    onCancelButtonClicked = {
                        navController.navigate(BPDMISScreen.Landing.name){
                            popUpTo(BPDMISScreen.ChangePasswordSuccess.name){inclusive = true}
                        }
                    }
                )
            }
        }
    }

}

@Composable
fun BPDMISBackBar(
    currentScreen : BPDMISScreen,
    navigateUp : () -> Unit = {},
    modifier : Modifier = Modifier
){
    val contextForToast = LocalContext.current.applicationContext

    TopAppBar(
        title = { Text(
            text = stringResource(currentScreen.title),
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        ) },
        modifier = Modifier,
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                    )
                }
            },
        backgroundColor = Color(0xFF0372D8)
        )
    }













