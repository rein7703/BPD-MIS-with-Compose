package com.example.bpdmiscompose.nav

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.PemdaBranchRoute
import com.example.bpdmiscompose.PemdaRoute
import com.example.bpdmiscompose.screens.PemdaSetoranSahamScreen


@Composable
fun pemdaNavGraph(
    navController: NavHostController,
    modifier : Modifier = Modifier,
){
    NavHost(
        navController= navController,
        route = PemdaRoute,
        startDestination = BPDMISScreen.PemdaFront.name
    ){
        composable(route = BPDMISScreen.PemdaFront.name){
            PemdaSetoranSahamScreen()
        }

        pemdaNavGraphBuild(navController = navController)
        landingNavGraph(navController = navController)
        changeNavGraph(navController = navController)
    }
}


fun NavGraphBuilder.pemdaNavGraphBuild(
    navController: NavHostController
){
    navigation(
        startDestination = BPDMISScreen.PemdaFront.name,
        route = PemdaBranchRoute
    ){
        // WILL BE FILLED LATER when the database has done //
        composable(route = BPDMISScreen.PemdaFront.name){
            Text(text = "ZZZZZZZZZZZZZz", fontSize = 80.sp)
        }
    }
}