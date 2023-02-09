package com.example.bpdmiscompose

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bpdmiscompose.components.*
import com.example.bpdmiscompose.nav.staffNavGraph
import com.example.bpdmiscompose.ui.BankStaffViewModel
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun BankStaffScreen(
    modifier: Modifier = Modifier,
    bankStaffViewModel : BankStaffViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    onClickSignOut : String = "",
    onClickChangePassword : String = "",
){
    val BankStaffUiState by bankStaffViewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val drawerItems = listOf<TextAndIcon>(
        TextAndIcon(stringResource(BPDMISScreen.ReadSetoranModal.title), Icons.Filled.Savings, destination = BPDMISScreen.ReadSetoranModal.name),
        TextAndIcon(stringResource(BPDMISScreen.ReadSkedulSetoran.title), Icons.Filled.CalendarMonth, destination = BPDMISScreen.ReadSkedulSetoran.name),
        TextAndIcon(stringResource(BPDMISScreen.ReadIndikatorKeuangan.title), Icons.Filled.Insights, destination = BPDMISScreen.ReadIndikatorKeuangan.name)
    )
    val buttonItems = listOf<ButtonInfo>(
        ButtonInfo(stringResource(R.string.change_password_header), backgroundColor = Color.Transparent, textColor = MaterialTheme.colors.onPrimary,outlined = true, onButtonClick = {navController.navigate(route = onClickChangePassword) }),
        ButtonInfo(stringResource(R.string.sign_out), backgroundColor = Color.Red, textColor = MaterialTheme.colors.onPrimary, onButtonClick = { navController.navigate(route = onClickSignOut){popUpTo(navController.graph.findStartDestination().id){inclusive = true }} })
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BPDMISScreen.valueOf(
        backStackEntry?.destination?.route ?: BPDMISScreen.Profile.name
    )
    val pagesWithMenuBar = listOf<BPDMISScreen>(
        BPDMISScreen.Profile,
        BPDMISScreen.ReadSetoranModal,
        BPDMISScreen.ReadSkedulSetoran,
        BPDMISScreen.ReadIndikatorKeuangan
    )

    val pagesWithNoBar = listOf<BPDMISScreen>(
        BPDMISScreen.ChangePasswordSuccess,
        BPDMISScreen.Landing,
        BPDMISScreen.Login,
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
                             navController.navigate(BPDMISScreen.Profile.name)
                         },
                         enableProfileButton = BankStaffUiState.enableProfileButton
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
        staffNavGraph(navController = navController, BankStaffUiState = BankStaffUiState)
    }
}




