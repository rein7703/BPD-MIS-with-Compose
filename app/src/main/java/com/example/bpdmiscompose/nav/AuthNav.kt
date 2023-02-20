package com.example.bpdmiscompose.nav

import androidx.compose.material.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.Graph
import com.example.bpdmiscompose.LoginScreen
import com.example.bpdmiscompose.ViewModels.AuthViewModel


fun NavGraphBuilder.authNavGraph(
    viewModel : AuthViewModel,
    navController: NavHostController
){
    navigation(
        startDestination = BPDMISScreen.Login.name,
        route = Graph.AuthRoute.name
    ){
        // Go to Login Page
        composable(route = BPDMISScreen.Login.name){
            LoginScreen(
                navController = navController,
                viewModel = viewModel,
                onStaffButtonClicked =  {
                    navController.navigate(BPDMISScreen.StaffPage.name){
                        navController.popBackStack()
                    }
                },
                onPemdaButtonClicked = {
                    navController.navigate(BPDMISScreen.Pemda.name) {
                        navController.popBackStack()
                    }
                },
                onAdminButtonClicked = {
                    navController.navigate(BPDMISScreen.AdminPage.name){
                        navController.popBackStack()
                    }
                }
            )
        }
        composable(route = "abc") {
            Text("BZZZZZZ")
        }
    }
}