package com.example.bpdmiscompose

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.bpdmiscompose.ViewModels.AuthViewModel
import com.example.bpdmiscompose.nav.authNavGraph
import com.example.bpdmiscompose.nav.changeNavGraph
import com.example.bpdmiscompose.screens.AdminScreen


val roboto = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_light, FontWeight.Light)
)

enum class Graph(@StringRes val title:Int){
    HomeRoute ( title = R.string.home_route),
    StaffRoute ( title = R.string.staff_route),
    AuthRoute ( title = R.string.auth_route),
    PemdaRoute (title = R.string.pemda_route),
    ChangePasswordRoute (title = R.string.change_password_route),
    StaffBranchRoute ( title = R.string.staff_route),
    PemdaBranchRoute ( title = R.string.pemda_branch),
    AdminBranchRoute (title = R.string.admin_branch),
    AdminRoute (title = R.string.admin_route),
    LandingRoute(title = R.string.landing_route)
}

const val HomeRoute = "home"
const val StaffRoute = "staff"
const val AuthRoute = "auth"
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
    AdminSetoranUpdate(title = R.string.admin_setoran_update),
    AdminSkedulAdd(title = R.string.admin_skedul_add),
    AdminSkedulCRUD(title = R.string.admin_skedul_crud),
    AdminManajemenPenggunaAdd(title = R.string.admin_manajemen_pengguna_add),

}


@Composable
fun BPDMISApp(
    viewModel : AuthViewModel,
    modifier: Modifier = Modifier,

){
    val navController : NavHostController = rememberNavController()
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = BPDMISScreen.valueOf(
        backStackEntry?.destination?.route ?: BPDMISScreen.Landing.name
    )
    NavHost(
        navController = navController,
        startDestination = Graph.LandingRoute.name,
        route = Graph.HomeRoute.name,
    ){

        // Go to the Landing Page and NavGraph
        landingNavGraph(viewModel = viewModel, navController = navController)
    }
}

fun NavGraphBuilder.landingNavGraph(
    viewModel: AuthViewModel,
    navController : NavHostController,

){
    navigation(
        route = Graph.LandingRoute.name,
        startDestination = BPDMISScreen.Landing.name
    ){

        // Go To Landing Page
        composable(route = BPDMISScreen.Landing.name){
            LandingScreen(
                onClickNextButton = {
                    navController.navigate(Graph.AuthRoute.name)
                }
            )
        }

        // Go to Authorization Graph
        authNavGraph(viewModel = viewModel, navController = navController)


        // Go to the Staff Pages Graph
        composable(route = BPDMISScreen.StaffPage.name){
            BankStaffScreen(
                viewModel = viewModel,
                navControllerOut = navController,
                onClickChangePassword = Graph.ChangePasswordRoute.name,
                onClickSignOut = Graph.LandingRoute.name,

            )
        }

        //Go to the Pemda Pages Graph
        composable(route = BPDMISScreen.Pemda.name){
            PemdaScreen(
                viewModel = viewModel,
                navControllerOut = navController,
                onClickChangePassword = Graph.ChangePasswordRoute.name,
                onClickSignOut = Graph.LandingRoute.name,

            )
        }

        //Go to the admin pages graph
        changeNavGraph(navController = navController)
        composable(route = BPDMISScreen.AdminPage.name){
            AdminScreen(
                viewModel = viewModel,
                navControllerOut = navController,
                onClickChangePassword = Graph.ChangePasswordRoute.name,
                onClickSignOut = Graph.LandingRoute.name,
            )
        }
    }
}
// MyApp.kt


