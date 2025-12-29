package com.danucdev.stocksystem.ui.screens.currentacounts.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.danucdev.stocksystem.domain.models.CurrentAccountModel
import com.danucdev.stocksystem.domain.models.TransactionModel
import com.danucdev.stocksystem.ui.core.ButtonTextItem
import com.danucdev.stocksystem.ui.core.CardBody
import com.danucdev.stocksystem.ui.core.ScreenTitle
import org.koin.compose.viewmodel.koinViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

class CurrentAccountDetailsScreen(clientId: Int) : Screen {

    private val clientRef = clientId

    @Composable
    override fun Content() {
        val viewmodel = koinViewModel<CurrentAccountsDetailsViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val clientDetails by viewmodel.accountDetails.collectAsState()
        val isLoading by viewmodel.isLoading.collectAsState()
        val transactions by viewmodel.transactions.collectAsState()
        val totalAmount by viewmodel.totalAmount.collectAsState()
        LaunchedEffect(true) {
            viewmodel.getDetails(clientRef)
        }

        Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                    return@Box
                }
                Header(
                    clientDetails!!,
                    navigator,
                    onNewDebt = { viewmodel.addDebt() },
                    onNewPayment = { viewmodel.addPayment() }
                )
                if (transactions?.isEmpty() == true) CardBody("No hay registro de transacciones")
                else {
                    transactions?.forEach { transaction ->
                        TransactionRowItem(transaction)
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    ScreenTitle( "Deuda total: $totalAmount")
                }
            }
        }
    }
}

@Composable
fun TransactionRowItem(transaction: TransactionModel) {

    val dateFormatter = DateTimeFormatter.ofPattern(
        "dd/MM/yyyy",
        Locale.getDefault()
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        CardBody(transaction.details)
        CardBody(transaction.amount)
        CardBody(dateFormatter.format(transaction.date))
    }
}

@Composable
private fun Header(
    client: CurrentAccountModel,
    navigator: Navigator,
    onNewDebt: () -> Unit,
    onNewPayment: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                "",
                modifier = Modifier.clickable { navigator.pop() },
                tint = Color.White
            )
            ScreenTitle("Cuenta corriente: ${client.clientName}")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ButtonTextItem("Añadir deuda") { onNewDebt() }
            ButtonTextItem("Realizar un pago") { onNewPayment() }
        }
    }
}