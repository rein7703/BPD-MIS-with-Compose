package com.example.bpdmiscompose.screens

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.ButtonInfo
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.TextAndIcon
import com.example.bpdmiscompose.ViewModels.AuthViewModel
import com.example.bpdmiscompose.components.Drawer
import com.example.bpdmiscompose.components.TopBarBack
import com.example.bpdmiscompose.components.TopBarMenu
import com.example.bpdmiscompose.nav.adminNavGraph
import com.example.bpdmiscompose.ui.BankStaffViewModel
import kotlinx.coroutines.launch


fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive =  true
    }
}


@Composable
fun AdminScreen(
    viewModel : AuthViewModel,
    modifier: Modifier = Modifier,
    bankStaffViewModel : BankStaffViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    navControllerOut:NavHostController,
    onClickSignOut : String = "",
    onClickChangePassword : String = "",

){
    val BankStaffUiState by bankStaffViewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val drawerItems = listOf<TextAndIcon>(
        TextAndIcon(
            stringResource(BPDMISScreen.AdminManajemenPengguna.title),
            Icons.Filled.People,
            destination = BPDMISScreen.AdminManajemenPengguna.name),
        TextAndIcon(
            stringResource(BPDMISScreen.AdminSetoranModal.title),
            Icons.Filled.Savings,
            destination = BPDMISScreen.AdminSetoranModal.name),
        TextAndIcon(stringResource(
            BPDMISScreen.AdminSkedulSetoran.title),
            Icons.Filled.CalendarMonth,
            destination = BPDMISScreen.AdminSkedulSetoran.name),
        TextAndIcon(
            stringResource(BPDMISScreen.AdminIndikatorKeuangan.title),
            Icons.Filled.Insights,
            destination = BPDMISScreen.AdminIndikatorKeuangan.name)
    )
    val buttonItems = listOf<ButtonInfo>(
        ButtonInfo(stringResource(R.string.change_password_header), backgroundColor = Color.Transparent, textColor = MaterialTheme.colors.onPrimary,outlined = true, onButtonClick = {navControllerOut.navigate(route = onClickChangePassword) }),
        ButtonInfo(
            stringResource(R.string.sign_out),
            backgroundColor = Color.Red,
            textColor = MaterialTheme.colors.onPrimary,
            onButtonClick = {
                viewModel.logout()
                navControllerOut.navigate(route = onClickSignOut){popUpTo(navControllerOut.graph.findStartDestination().id){inclusive = true }}
            }
        )
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BPDMISScreen.valueOf(
        backStackEntry?.destination?.route ?: BPDMISScreen.AdminProfile.name
    )
    val pagesWithMenuBar = listOf<BPDMISScreen>(
        BPDMISScreen.AdminProfile,
        BPDMISScreen.AdminSetoranModal,
        BPDMISScreen.AdminSkedulSetoran,
        BPDMISScreen.AdminIndikatorKeuangan,
        BPDMISScreen.AdminManajemenPengguna
    )
    val pagesWithNoBar = listOf<BPDMISScreen>(
        BPDMISScreen.ChangePasswordSuccess
    )
    val topBarMenuState = rememberSaveable {(mutableStateOf(true))}
    val topBarBackState = rememberSaveable {(mutableStateOf(false))}
    if (pagesWithMenuBar.contains(currentScreen) ){
        topBarMenuState.value = true
        topBarBackState.value = false
    } else if(pagesWithNoBar.contains(currentScreen)){
        topBarMenuState.value = false
        topBarBackState.value = false
    } else {
        topBarMenuState.value = false
        topBarBackState.value = true
    }
    Scaffold(
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
                    onProfileButtonClick = {
                        navController.navigate(BPDMISScreen.AdminProfile.name){launchSingleTop= true}
                    },
                    enableProfileButton = (topBarMenuState.value and (currentScreen != BPDMISScreen.AdminProfile))
                )
            } else if(topBarBackState.value){
                TopBarBack(title = stringResource(currentScreen.title), navigateUp = {navController.navigateUp()})
            }
        },
        drawerContent = if(!topBarMenuState.value) null else {{
            Drawer(
                icon = Icons.Filled.AccountCircle,
                name = BankStaffUiState.staffName,
                jabatan = BankStaffUiState.staffJabatan,
                items = drawerItems,
                buttons = buttonItems,
                navController = navController,
                scaffoldState = scaffoldState,
                scope = scope
            )
        }},
        drawerGesturesEnabled = topBarMenuState.value,
        drawerBackgroundColor = if(topBarMenuState.value) MaterialTheme.colors.primary else Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {innerPadding ->
        adminNavGraph(navController = navController)

    }
}