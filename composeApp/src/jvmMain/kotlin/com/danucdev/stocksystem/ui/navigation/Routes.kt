package com.danucdev.stocksystem.ui.navigation

sealed class Routes(val route:String){
    data object MainPanel:Routes("mainpanel")
    data object Canchas:Routes("canchas")
    data object CuentasCorrientes:Routes("cuentasCorrientes")
}
