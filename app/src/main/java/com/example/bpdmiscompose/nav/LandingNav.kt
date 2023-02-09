package com.example.bpdmiscompose.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bpdmiscompose.*
import com.example.bpdmiscompose.screens.AdminScreen


fun NavGraphBuilder.landingNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = BPDMISScreen.Login.name,
        route = LandingRoute
    ){

        // Go to Login Page
        composable(route = BPDMISScreen.Login.name){
            LoginScreen(
                onStaffButtonClicked =  {
                    navController.navigate(BPDMISScreen.StaffPage.name){
                        popUpTo(BPDMISScreen.Landing.name){inclusive = true}
                    }
                },
                onPemdaButtonClicked = {
                    navController.navigate(BPDMISScreen.Pemda.name){
                        popUpTo(BPDMISScreen.Landing.name){inclusive = false}
                    }
                },
                onAdminButtonClicked = {
                    navController.navigate(BPDMISScreen.AdminPage.name){
                        popUpTo(BPDMISScreen.Landing.name){inclusive = true}
                    }
                }
            )
        }

        composable(route = BPDMISScreen.StaffPage.name){
            BankStaffScreen(
                onClickChangePassword = BPDMISScreen.ChangePassword.name,
                onClickSignOut = BPDMISScreen.Landing.name,
            )
        }
        
        composable(route = BPDMISScreen.Pemda.name){
            PemdaScreen(
                onClickChangePassword = BPDMISScreen.ChangePassword.name,
                onClickSignOut = BPDMISScreen.Landing.name,
            )
        }

        composable(route = BPDMISScreen.AdminPage.name){
            AdminScreen(
                onClickSignOut = BPDMISScreen.Landing.name,
                onClickChangePassword = BPDMISScreen.ChangePassword.name,
            )
        }
    }
}