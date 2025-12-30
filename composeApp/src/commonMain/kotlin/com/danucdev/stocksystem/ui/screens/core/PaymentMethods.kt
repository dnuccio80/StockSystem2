package com.danucdev.stocksystem.ui.screens.core

sealed class PaymentMethods(val method:String) {
    data object Cash:PaymentMethods("Efectivo")
    data object MoneyTransfer:PaymentMethods("Transferencia")
}
