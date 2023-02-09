package com.example.bpdmiscompose.nav

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.bpdmiscompose.*


/*
@Composable
fun changeNavGraph(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        route = ChangePasswordRoute,
        startDestination = BankStaffScreen.ChangePassword.name
    ){
        changeNavGraph(navController=navController)
    }
} */

fun NavGraphBuilder.changeNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = BPDMISScreen.ChangePassword.name,
        route = ChangePasswordRoute
    ){
        //Go to Change Password Page
        composable (route = BPDMISScreen.ChangePassword.name){
            ChangePasswordScreen(
                onClickNextButton = {
                    navController.navigate(BPDMISScreen.ChangePasswordSuccess.name){
                        popUpTo(navController.graph.findStartDestination().id){
                            inclusive = true
                        }
                    }
                }
            )
        }
        //Go to Successfully Change Password Page
        composable(route = BPDMISScreen.ChangePasswordSuccess.name){
            ChangePasswordSuccessScreen(
                onClickCancelButton = {
                    navController.navigate(route = BPDMISScreen.Landing.name){
                        popUpTo(BPDMISScreen.ChangePasswordSuccess.name){inclusive = true}
                    }
                }
            )
        }

        composable(route = BPDMISScreen.Landing.name){
            BPDMISApp()
        }
    }

}