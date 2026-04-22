package com.smartaccounting.core.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object ResetPassword : Screen("reset_password")
    
    object Main : Screen("main")
    object Dashboard : Screen("dashboard")
    object BillList : Screen("bill_list")
    object AddBill : Screen("add_bill")
    object EditBill : Screen("edit_bill/{billId}") {
        fun createRoute(billId: String) = "edit_bill/$billId"
    }
    object BillDetail : Screen("bill_detail/{billId}") {
        fun createRoute(billId: String) = "bill_detail/$billId"
    }
    
    object TagList : Screen("tag_list")
    object AddTag : Screen("add_tag")
    object EditTag : Screen("edit_tag/{tagId}") {
        fun createRoute(tagId: String) = "edit_tag/$tagId"
    }
    
    object Statistics : Screen("statistics")
    object ExpenseRanking : Screen("expense_ranking")
    
    object Settings : Screen("settings")
    object Profile : Screen("profile")
    object Security : Screen("security")
    object Backup : Screen("backup")
    object About : Screen("about")
}
