package com.danucdev.stocksystem.ui

import com.danucdev.stocksystem.ui.screens.clients.ClientsViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModel { ClientsViewModel(get(), get()) }
}