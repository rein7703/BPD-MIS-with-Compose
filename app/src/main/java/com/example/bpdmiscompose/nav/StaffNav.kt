package com.example.bpdmiscompose.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bpdmiscompose.*
import com.example.bpdmiscompose.screens.staffscreens.*
import com.example.bpdmiscompose.ui.BankStaffUiState


@Composable
fun staffNavGraph(
    navController: NavHostController,
    BankStaffUiState: BankStaffUiState
){
    NavHost(
        navController= navController,
        route = StaffRoute,
        startDestination = BPDMISScreen.Profile.name
    ){
        composable(route = BPDMISScreen.Profile.name) {
            BankStaffUiState.enableProfileButton = false
            BankStaffProfileLayout(
                staffName = BankStaffUiState.staffName,
                staffJabatan = BankStaffUiState.staffJabatan,
                staffAddress = BankStaffUiState.staffAddress,
                staffEmail = BankStaffUiState.staffEmail,
                staffPhoneNumber = BankStaffUiState.staffPhoneNumber,
                staffId = BankStaffUiState.staffID,
            )

        }
        staffNavGraphBuild(navController, BankStaffUiState)
        changeNavGraph(navController = navController)
        landingNavGraph(navController)
        //val BankStaffUiState by bankStaffViewModel.uiState.collectAsState()

    }
}

fun NavGraphBuilder.staffNavGraphBuild(
    navController: NavHostController,
    BankStaffUiState: BankStaffUiState
){
    navigation(
        //navController = navController,
        route = StaffBranchRoute,
        startDestination = BPDMISScreen.Profile.name
    )
    {
        composable(route = BPDMISScreen.ReadSetoranModal.name) {
            BankStaffUiState.enableProfileButton = true
            StaffSetoranModalLayout()
        }
        composable(route = BPDMISScreen.ReadSkedulSetoran.name) {
            BankStaffUiState.enableProfileButton = true
            StaffSkedulSetoranModalLayout(navController = navController)
        }
        composable(route = BPDMISScreen.ReadIndikatorKeuangan.name) {
            BankStaffUiState.enableProfileButton = true
            StaffIndikatorKeuanganUtamaLayout(navController = navController)
        }

        composable(route = BPDMISScreen.StaffSemuaSetoran.name){
            BankStaffUiState.enableProfileButton = false
            StaffSemuaSetoranLayout()
        }
        composable(route = BPDMISScreen.StaffSetoranBerdasarkanPemda.name){
            BankStaffUiState.enableProfileButton = false
            StaffSetoranBerdasarkanPemda()
        }

        composable(route=BPDMISScreen.StaffSetoranBerdasarkanTahun.name){
            BankStaffUiState.enableProfileButton = false
            StaffSetoranBerdasarkanTahun()
        }

        composable(route=BPDMISScreen.StaffIndikatorSearchResult.name){
            BankStaffUiState.enableProfileButton = false
            StaffIndikatorSearchResultScreen()
        }


        //landingNavGraph(navController = navController)
    }
}

