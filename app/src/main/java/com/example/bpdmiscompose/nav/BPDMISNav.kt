package com.example.bpdmiscompose

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bpdmiscompose.nav.changeNavGraph
import com.example.bpdmiscompose.nav.landingNavGraph
import com.example.bpdmiscompose.nav.pemdaNavGraphBuild
import com.example.bpdmiscompose.nav.staffNavGraphBuild
import com.example.bpdmiscompose.ui.BankStaffUiState


val roboto = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_light, FontWeight.Light)
)

const val HomeRoute = "home"
const val StaffRoute = "staff"
const val LandingRoute = "land"
const val PemdaRoute = "pemda"
const val ChangePasswordRoute = "change"
const val StaffBranchRoute = "staff_branch"
const val PemdaBranchRoute = "pemda_branch"
const val AdminBranchRoute = "admin_branch"
const val AdminRoute = "admin"


enum class BPDMISScreen(@StringRes val title:Int){
    Profile(title = R.string.bank_staff_profile),
    ReadSetoranModal(title = R.string.setoran_modal),
    ReadSkedulSetoran(title = R.string.skedul_setoran_modal),
    ReadIndikatorKeuangan(title = R.string.indikator_keuangan_utama),
    Landing(title = R.string.app_name),
    Login(title = R.string.login),
    Pemda(title = R.string.pemda_screen),
    PemdaFront(title = R.string.pemda_front),
    StaffPage(title = R.string.staff_page),
    ChangePassword(title = R.string.change_password_header),
    ChangePasswordSuccess(title = R.string.selamat),
    StaffIndikatorSearchResult(title = R.string.hasil_penelusuran_kinerja),
    StaffSemuaSetoran(title = R.string.semua_data_setoran),
    StaffSetoranBerdasarkanTahun(title = R.string.setoran_berdasarkan_tahun),
    StaffSetoranBerdasarkanPemda(title = R.string.setoran_berdasarkan_pemda),
    AdminPage(title = R.string.admin_page),
    AdminProfile(title = R.string.admin_profile),
    AdminSetoranModal(title = R.string.admin_setoran_modal),
    AdminManajemenPengguna(title = R.string.admin_manajemen_pengguna),
    AdminSkedulSetoran(title = R.string.admin_skedul_setoran),
    AdminIndikatorKeuangan(title = R.string.admin_indikator_keuangan),
    AdminIndikatorAdd(title = R.string.admin_indikator_add ),
    AdminIndikatorSearchResult(title = R.string.admin_indikator_search_result),
    AdminPenggunaAdd(title = R.string.admin_pengguna_add),
    AdminSetoranAdd(title = R.string.admin_setoran_add),
    AdminSkedulAdd(title = R.string.admin_skedul_add),
    AdminSkedulCRUD(title = R.string.admin_skedul_crud),
    AdminManajemenPenggunaAdd(title = R.string.admin_manajemen_pengguna_add),
}

@Composable
fun BPDMISApp(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = BPDMISScreen.valueOf(
        backStackEntry?.destination?.route ?: BPDMISScreen.Landing.name
    )
    NavHost(
        navController = navController,
        startDestination = BPDMISScreen.Landing.name,
        route = HomeRoute
    ){
        composable(route = BPDMISScreen.Landing.name){
            LandingScreen(
                onClickNextButton = {
                    navController.navigate(BPDMISScreen.Login.name){
                        popUpTo(0){inclusive = true}
                    }
                }
            )
        }
        landingNavGraph(navController = navController)
        staffNavGraphBuild(navController = navController, BankStaffUiState())
        changeNavGraph(navController = navController)
        pemdaNavGraphBuild(navController = navController)

    }

}


// MyApp.kt


