package com.danucdev.stocksystem.ui.di

import com.danucdev.stocksystem.ui.screens.clients.ClientsViewModel
import com.danucdev.stocksystem.ui.screens.concessions.ConcessionsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { ClientsViewModel(get(), get(), get(), get()) }
    viewModel { ConcessionsViewModel(get(), get(), get(), get()) }
}