package com.danucdev.stocksystem.ui.screens.currentacounts.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.danucdev.stocksystem.ui.core.TitleAndButtonRowItemScreen
import com.danucdev.stocksystem.ui.core.TitleAndButtonRowItemScreenWithSearchBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CurrentAccountDetailsScreen(clientId:Int) {

    val viewmodel = koinViewModel<CurrentAccountsDetailsViewModel>()

    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TitleAndButtonRowItemScreen(
                title = "Cuenta corriente de XXX",
                buttonText = "Realizar un pago",
                onButtonClick = { }
            )
        }
    }
}
