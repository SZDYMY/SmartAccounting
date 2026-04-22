package com.smartaccounting.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.smartaccounting.core.presentation.ui.auth.LoginScreen
import com.smartaccounting.core.presentation.ui.auth.RegisterScreen
import com.smartaccounting.core.presentation.ui.auth.ResetPasswordScreen
import com.smartaccounting.core.presentation.ui.bill.AddBillScreen
import com.smartaccounting.core.presentation.ui.bill.BillDetailScreen
import com.smartaccounting.core.presentation.ui.bill.BillListScreen
import com.smartaccounting.core.presentation.ui.bill.EditBillScreen
import com.smartaccounting.core.presentation.ui.main.DashboardScreen
import com.smartaccounting.core.presentation.ui.settings.ProfileScreen
import com.smartaccounting.core.presentation.ui.settings.SecurityScreen
import com.smartaccounting.core.presentation.ui.settings.SettingsScreen
import com.smartaccounting.core.presentation.ui.statistics.ExpenseRankingScreen
import com.smartaccounting.core.presentation.ui.statistics.StatisticsScreen
import com.smartaccounting.core.presentation.ui.tag.AddTagScreen
import com.smartaccounting.core.presentation.ui.tag.TagListScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onNavigateToResetPassword = { navController.navigate(Screen.ResetPassword.route) },
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.ResetPassword.route) {
            ResetPasswordScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToBillList = { navController.navigate(Screen.BillList.route) },
                onNavigateToStatistics = { navController.navigate(Screen.Statistics.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        
        composable(Screen.BillList.route) {
            BillListScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddBill = { navController.navigate(Screen.AddBill.route) },
                onNavigateToBillDetail = { billId ->
                    navController.navigate(Screen.BillDetail.createRoute(billId))
                }
            )
        }
        
        composable(Screen.AddBill.route) {
            AddBillScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.EditBill.route,
            arguments = listOf(navArgument("billId") { type = NavType.StringType })
        ) { backStackEntry ->
            val billId = backStackEntry.arguments?.getString("billId") ?: ""
            EditBillScreen(
                billId = billId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.BillDetail.route,
            arguments = listOf(navArgument("billId") { type = NavType.StringType })
        ) { backStackEntry ->
            val billId = backStackEntry.arguments?.getString("billId") ?: ""
            BillDetailScreen(
                billId = billId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { navController.navigate(Screen.EditBill.createRoute(billId)) }
            )
        }
        
        composable(Screen.TagList.route) {
            TagListScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddTag = { navController.navigate(Screen.AddTag.route) }
            )
        }
        
        composable(Screen.AddTag.route) {
            AddTagScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Statistics.route) {
            StatisticsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToExpenseRanking = { navController.navigate(Screen.ExpenseRanking.route) }
            )
        }
        
        composable(Screen.ExpenseRanking.route) {
            ExpenseRankingScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToSecurity = { navController.navigate(Screen.Security.route) }
            )
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Security.route) {
            SecurityScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
