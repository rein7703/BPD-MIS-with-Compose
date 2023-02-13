package com.example.bpdmiscompose

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bpdmiscompose.components.Drawer
import com.example.bpdmiscompose.components.TopBarBack
import com.example.bpdmiscompose.components.TopBarMenu
import com.example.bpdmiscompose.nav.pemdaNavGraph
import kotlinx.coroutines.launch


@Composable
fun PemdaScreen(
    modifier : Modifier = Modifier,
    onClickChangePassword : String,
    onClickSignOut : String,
    navController: NavHostController = rememberNavController(),
    navControllerOut : NavHostController,

){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val buttonItems = listOf<ButtonInfo>(
        ButtonInfo(
            stringResource(R.string.change_password_header),
            backgroundColor = Color.Transparent,
            textColor = MaterialTheme.colors.onPrimary,
            outlined = true,
            onButtonClick = {
                navControllerOut.navigate(route = onClickChangePassword)
            }
        ),
        ButtonInfo(
            stringResource(R.string.sign_out),
            backgroundColor = Color.Red,
            textColor = MaterialTheme.colors.onPrimary,
            onButtonClick = {
                navControllerOut.navigate(
                    route = onClickSignOut){
                    popUpTo(navControllerOut.graph.findStartDestination().id){inclusive = true }
                }
            }
        )
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BPDMISScreen.valueOf(
        backStackEntry?.destination?.route ?: BPDMISScreen.Profile.name
    )
    val topBarMenuState = rememberSaveable {(mutableStateOf(true))}
    val topBarBackState = rememberSaveable {(mutableStateOf(false))}
    when(currentScreen){
        BPDMISScreen.PemdaFront -> {
            topBarMenuState.value = true
            topBarBackState.value = false
        }
        BPDMISScreen.ChangePassword -> {
            topBarMenuState.value = false
            topBarBackState.value = true
        }
        else -> {
            topBarMenuState.value = false
            topBarBackState.value = false
        }
    }
    Scaffold (
        scaffoldState = scaffoldState,
        topBar = {
            if(topBarMenuState.value){
                TopBarMenu(
                    currentScreen = currentScreen,
                    onNavigationIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    },
                )
            } else if(topBarBackState.value){
                TopBarBack(title = stringResource(currentScreen.title), navigateUp = {navController.navigateUp()})
            }
        },
        drawerContent = if(!topBarMenuState.value) null else {{
            Drawer(
                icon = Icons.Filled.AccountCircle,
                name = stringResource(R.string.nama),
                jabatan = stringResource(R.string.jabatan),
                buttons = buttonItems,
                navController = navController,
                scaffoldState = scaffoldState,
                scope = scope
            )
        }},
        drawerGesturesEnabled = true,
        drawerBackgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){innerPadding->
        pemdaNavGraph(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}
