package com.example.bpdmiscompose

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bpdmiscompose.ViewModels.AuthViewModel
import com.example.bpdmiscompose.ViewModels.UserDataViewModel
import com.example.bpdmiscompose.components.Drawer
import com.example.bpdmiscompose.components.TopBarBack
import com.example.bpdmiscompose.components.TopBarMenu
import com.example.bpdmiscompose.nav.staffNavGraph
import com.example.bpdmiscompose.ui.BankStaffViewModel
import kotlinx.coroutines.launch


@Composable
fun BankStaffScreen(
    viewModel : AuthViewModel,
    modifier: Modifier = Modifier,
    bankStaffViewModel : BankStaffViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    navControllerOut : NavHostController,
    onClickSignOut : String = "",
    onClickChangePassword : String = "",
    userDataViewModel : UserDataViewModel = hiltViewModel()
){
    val loginFlow = viewModel.loginFlow.collectAsState()
    val BankStaffUiState by bankStaffViewModel.uiState.collectAsState()
    LaunchedEffect(key1 = Unit){userDataViewModel.getUserData(viewModel.currentUser?.email ?: "No Email")}
    val userDataUiState = userDataViewModel.userDataUiState
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val drawerItems = listOf<TextAndIcon>(
        TextAndIcon(stringResource(BPDMISScreen.ReadSetoranModal.title), Icons.Filled.Savings, destination = BPDMISScreen.ReadSetoranModal.name),
        TextAndIcon(stringResource(BPDMISScreen.ReadSkedulSetoran.title), Icons.Filled.CalendarMonth, destination = BPDMISScreen.ReadSkedulSetoran.name),
        TextAndIcon(stringResource(BPDMISScreen.ReadIndikatorKeuangan.title), Icons.Filled.Insights, destination = BPDMISScreen.ReadIndikatorKeuangan.name)
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
                name = userDataUiState.userData.data?.name ?: "Nama",
                jabatan = userDataUiState.userData.data?.jabatan ?: "Jabatan",
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
        staffNavGraph(navController = navController, BankStaffUiState = BankStaffUiState, viewModel = viewModel)
    }
}





