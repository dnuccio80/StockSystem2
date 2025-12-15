package com.danucdev.stocksystem.ui.navigation

sealed class Routes(val route:String){
    data object MainPanel:Routes("mainpanel")
    data object Turns:Routes("turns")
    data object OpenAccounts:Routes("openAccounts")
    data object Inventory:Routes("inventory")
    data object Clients:Routes("clients")
    data object Tournaments:Routes("tournaments")
}
