package com.example.bpdmiscompose.nav


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bpdmiscompose.AdminBranchRoute
import com.example.bpdmiscompose.AdminRoute
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.ViewModels.IndikatorKeuanganViewModel
import com.example.bpdmiscompose.ViewModels.RBBViewModel
import com.example.bpdmiscompose.ViewModels.SkedulSetoranViewModel
import com.example.bpdmiscompose.ViewModels.StaffSetoranModalViewModel
import com.example.bpdmiscompose.screens.AdminIndikatorKeuanganUtamaLayout
import com.example.bpdmiscompose.screens.AdminManajemenPenggunaLayout
import com.example.bpdmiscompose.screens.AdminSetoranModalLayout
import com.example.bpdmiscompose.screens.AdminSkedulSetoranModalLayout
import com.example.bpdmiscompose.screens.adminscreens.*


@Composable
fun adminNavGraph(
    navController: NavHostController,
    modifier : Modifier = Modifier,
    staffSetoranModalViewModel: StaffSetoranModalViewModel = hiltViewModel(),
    skedulSetoranViewModel: SkedulSetoranViewModel = hiltViewModel(),
    indikatorKeuanganViewModel: IndikatorKeuanganViewModel = hiltViewModel(),
    rbbViewModel: RBBViewModel = hiltViewModel()
){
    NavHost(
        navController= navController,
        route = AdminRoute,
        startDestination = BPDMISScreen.AdminProfile.name
    ){
        composable(route = BPDMISScreen.AdminProfile.name){
            AdminProfileLayout(
                adminName = stringResource(id = R.string.nama),
                adminEmail = stringResource(id = R.string.email),
                adminAddress = stringResource(id = R.string.alamat),
                adminPhoneNumber = stringResource(id = R.string.nomor_hp),
                adminId = stringResource(id = R.string.id),
                adminJabatan = stringResource(id = R.string.jabatan),
            )
        }

        adminNavGraphBuild(navController = navController, staffSetoranModalViewModel = staffSetoranModalViewModel, skedulSetoranViewModel = skedulSetoranViewModel, indikatorKeuanganViewModel = indikatorKeuanganViewModel, rbbViewModel = rbbViewModel)
        changeNavGraph(navController = navController)
    }
}


fun NavGraphBuilder.adminNavGraphBuild(
    navController: NavHostController,
    staffSetoranModalViewModel: StaffSetoranModalViewModel,
    skedulSetoranViewModel: SkedulSetoranViewModel,
    indikatorKeuanganViewModel: IndikatorKeuanganViewModel,
    rbbViewModel: RBBViewModel
){
    navigation(
        startDestination = BPDMISScreen.AdminProfile.name,
        route = AdminBranchRoute
    ){

        composable(route = BPDMISScreen.AdminManajemenPengguna.name){
            AdminManajemenPenggunaLayout(navController = navController)
        }

        composable(route = BPDMISScreen.AdminManajemenPenggunaAdd.name){
            AdminManajemenPenggunaAddLayout()
        }

        composable(route = BPDMISScreen.AdminSetoranModal.name){
            AdminSetoranModalLayout(navController = navController, staffSetoranModalViewModel = staffSetoranModalViewModel)
        }

        composable(route = BPDMISScreen.AdminSetoranAdd.name){
            AdminSetoranAddLayout(staffSetoranModalViewModel = staffSetoranModalViewModel)
        }

        composable(route = BPDMISScreen.AdminSetoranUpdate.name){
            AdminSetoranUpdateLayout(staffSetoranModalViewModel = staffSetoranModalViewModel)
        }


        composable(route = BPDMISScreen.AdminSkedulSetoran.name){
            AdminSkedulSetoranModalLayout(navController = navController, skedulSetoranViewModel = skedulSetoranViewModel)
        }

        composable(route = BPDMISScreen.AdminIndikatorKeuangan.name){
            AdminIndikatorKeuanganUtamaLayout(navController = navController, indikatorKeuanganViewModel = indikatorKeuanganViewModel, rbbViewModel = rbbViewModel)
        }

        composable(route = BPDMISScreen.AdminIndikatorAdd.name){
            AdminIndikatorAddLayout(indikatorKeuanganViewModel = indikatorKeuanganViewModel, rbbViewModel = rbbViewModel)
        }
        
        composable(route = BPDMISScreen.AdminIndikatorSearchResult.name){
            AdminIndikatorSearchResultLayout(navController = navController, indikatorKeuanganViewModel = indikatorKeuanganViewModel, rbbViewModel = rbbViewModel)
        }

        composable(route = BPDMISScreen.AdminIndikatorAllData.name){
            AdminIndikaotrAllData(indikatorKeuanganViewModel = indikatorKeuanganViewModel, rbbViewModel = rbbViewModel)
        }

        composable(route = BPDMISScreen.AdminSkedulCRUD.name){
            AdminSkedulCRUDLayout(navController = navController, skedulSetoranViewModel = skedulSetoranViewModel)
        }

        composable(route = BPDMISScreen.AdminSkedulAdd.name){
            AdminSkedulAddLayout(skedulSetoranViewModel = skedulSetoranViewModel)
        }

        composable(route = BPDMISScreen.AdminSkedulUpdate.name){
            AdminSkedulUpdateLayout(skedulSetoranViewModel = skedulSetoranViewModel)
        }
        composable(route = BPDMISScreen.AdminSkedulBerdasarkanTahun.name){
            AdminSkedulBerdasarkanTahunLayout(skedulSetoranViewModel = skedulSetoranViewModel)
        }

        composable(route = BPDMISScreen.AdminSkedulBerdasarkanPemda.name){
            AdminSkedulBerdasarkanPemdaLayout(skedulSetoranViewModel = skedulSetoranViewModel)
        }


    }
}