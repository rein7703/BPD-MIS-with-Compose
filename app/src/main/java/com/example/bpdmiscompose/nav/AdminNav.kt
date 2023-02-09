package com.example.bpdmiscompose.nav


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bpdmiscompose.AdminBranchRoute
import com.example.bpdmiscompose.AdminRoute
import com.example.bpdmiscompose.BPDMISScreen
import com.example.bpdmiscompose.R
import com.example.bpdmiscompose.screens.AdminIndikatorKeuanganUtamaLayout
import com.example.bpdmiscompose.screens.AdminManajemenPenggunaLayout
import com.example.bpdmiscompose.screens.AdminSetoranModalLayout
import com.example.bpdmiscompose.screens.AdminSkedulSetoranModalLayout
import com.example.bpdmiscompose.screens.adminscreens.*
import com.example.bpdmiscompose.screens.staffscreens.StaffSetoranBerdasarkanPemda
import com.example.bpdmiscompose.screens.staffscreens.StaffSetoranBerdasarkanTahun


@Composable
fun adminNavGraph(
    navController: NavHostController,
    modifier : Modifier = Modifier,
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

        adminNavGraphBuild(navController = navController)
        landingNavGraph(navController = navController)
        changeNavGraph(navController = navController)
    }
}


fun NavGraphBuilder.adminNavGraphBuild(
    navController: NavHostController
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
            AdminSetoranModalLayout(navController = navController)
        }

        composable(route = BPDMISScreen.AdminSetoranAdd.name){
            AdminSetoranAddLayout()
        }

        composable(route = BPDMISScreen.AdminSkedulSetoran.name){
            AdminSkedulSetoranModalLayout(navController = navController)
        }

        composable(route = BPDMISScreen.AdminIndikatorKeuangan.name){
            AdminIndikatorKeuanganUtamaLayout(navController = navController)
        }

        composable(route = BPDMISScreen.AdminIndikatorAdd.name){
            AdminIndikatorAddLayout()
        }
        
        composable(route = BPDMISScreen.AdminIndikatorSearchResult.name){
            AdminIndikatorSearchResultLayout(navController = navController)
        }

        composable(route = BPDMISScreen.AdminSkedulCRUD.name){
            AdminSkedulCRUDLayout(navController = navController)
        }

        composable(route = BPDMISScreen.AdminSkedulAdd.name){
            AdminSkedulAddLayout()
        }

        composable(route = BPDMISScreen.StaffSetoranBerdasarkanPemda.name){
            StaffSetoranBerdasarkanPemda()
        }
        composable(route = BPDMISScreen.StaffSetoranBerdasarkanTahun.name){
            StaffSetoranBerdasarkanTahun()
        }
    }
}