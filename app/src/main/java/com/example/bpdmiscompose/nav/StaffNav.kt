package com.example.bpdmiscompose.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.bpdmiscompose.*
import com.example.bpdmiscompose.ViewModels.*
import com.example.bpdmiscompose.screens.staffscreens.*
import com.example.bpdmiscompose.ui.BankStaffUiState


@Composable
fun staffNavGraph(
    navController: NavHostController,
    BankStaffUiState: BankStaffUiState = BankStaffUiState(),
    skedulSetoranViewModel: SkedulSetoranViewModel = hiltViewModel(),
    indikatorKeuanganViewModel: IndikatorKeuanganViewModel = hiltViewModel(),
    rbbViewModel: RBBViewModel = hiltViewModel(),
    userDataViewModel : UserDataViewModel = hiltViewModel(),
    viewModel: AuthViewModel,


    ){
    val userDataUiState = userDataViewModel.userDataUiState
    NavHost(
        navController= navController,
        route = Graph.StaffRoute.name,
        startDestination = BPDMISScreen.Profile.name
    ){
        composable(route = BPDMISScreen.Profile.name) {
            BankStaffProfileLayout(
                staffName = userDataUiState.userData.data?.name ?: BankStaffUiState.staffName,
                staffJabatan = userDataUiState.userData.data?.jabatan ?: BankStaffUiState.staffJabatan,
                staffEmail = userDataUiState.userData.data?.email ?: BankStaffUiState.staffEmail,
                staffPhoneNumber = userDataUiState.userData.data?.nomorHP ?: BankStaffUiState.staffPhoneNumber,
                staffId = userDataUiState.userData.data?.idPegawai ?: BankStaffUiState.staffID,
            )
        }
        staffNavGraphBuild(navController, BankStaffUiState = BankStaffUiState, skedulSetoranViewModel = skedulSetoranViewModel, indikatorKeuanganViewModel = indikatorKeuanganViewModel, rbbViewModel = rbbViewModel)
        changeNavGraph(navController = navController, viewModel = viewModel)
        //landingNavGraph(navController = navController, auth = auth)
    }
}

fun NavGraphBuilder.staffNavGraphBuild(
    navController: NavHostController,
    BankStaffUiState: BankStaffUiState,
    skedulSetoranViewModel: SkedulSetoranViewModel,
    indikatorKeuanganViewModel: IndikatorKeuanganViewModel,
    rbbViewModel: RBBViewModel
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
            StaffSkedulSetoranModalLayout(navController = navController, skedulSetoranViewModel = skedulSetoranViewModel)
        }
        composable(route = BPDMISScreen.ReadIndikatorKeuangan.name) {
            BankStaffUiState.enableProfileButton = true
            StaffIndikatorKeuanganUtamaLayout(navController = navController, indikatorKeuanganViewModel = indikatorKeuanganViewModel, rbbViewModel = rbbViewModel)
        }

        composable(route = BPDMISScreen.StaffSemuaSkedul.name){
            BankStaffUiState.enableProfileButton = false
            StaffSemuaSkedulLayout(skedulSetoranViewModel = skedulSetoranViewModel)
        }
        composable(route = BPDMISScreen.StaffSkedulBerdasarkanPemda.name){
            BankStaffUiState.enableProfileButton = false
            StaffSkedulBerdasarkanPemda(skedulSetoranViewModel = skedulSetoranViewModel)
        }

        composable(route=BPDMISScreen.StaffSkedulBerdasarkanTahun.name){
            BankStaffUiState.enableProfileButton = false
            StaffSetoranBerdasarkanTahun(skedulSetoranViewModel = skedulSetoranViewModel)
        }

        composable(route=BPDMISScreen.StaffIndikatorSearchResult.name){
            BankStaffUiState.enableProfileButton = false
            StaffIndikatorSearchResultScreen(navController = navController, indikatorKeuanganViewModel = indikatorKeuanganViewModel, rbbViewModel = rbbViewModel)
        }

    }
}

