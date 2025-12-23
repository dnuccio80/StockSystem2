package com.danucdev.stocksystem.ui.screens.currentacounts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.danucdev.stocksystem.ui.core.ButtonTextItem
import com.danucdev.stocksystem.ui.core.ScreenTitle
import com.danucdev.stocksystem.ui.core.TitleAndButtonRowItemScreen
import com.danucdev.stocksystem.ui.core.TitleAndButtonRowItemScreenWithSearchBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OpenAccountsScreen() {

    val viewmodel = koinViewModel<OpenAccountsViewModel>()

    val query by viewmodel.query.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TitleAndButtonRowItemScreenWithSearchBar(
                title = "Cuentas Corrientes",
                buttonText = "Nueva cuenta corriente",
                onButtonClick = { viewmodel.updateShowAddAccount(true) },
                query = query,
                onSearchValueChange = { viewmodel.updateQuery(it) }
            )
        }
    }
}

