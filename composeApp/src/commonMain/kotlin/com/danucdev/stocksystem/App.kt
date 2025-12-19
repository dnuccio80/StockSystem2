package com.danucdev.stocksystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.danucdev.stocksystem.data.di.dataModule
import com.danucdev.stocksystem.data.di.platformModule
import com.danucdev.stocksystem.domain.domainModule
import com.danucdev.stocksystem.ui.uiModule
import com.danucdev.stocksystem.ui.screens.core.NavigationWrapper
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    MaterialTheme {

        KoinApplication(application = {
            modules(
                dataModule,
                uiModule,
                domainModule,
                platformModule()
            )
        }) {
            NavigationWrapper()
        }
    }
}