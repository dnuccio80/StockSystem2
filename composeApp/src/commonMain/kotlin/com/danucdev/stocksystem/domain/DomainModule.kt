package com.danucdev.stocksystem.domain

import com.danucdev.stocksystem.domain.usecases.AddClient
import com.danucdev.stocksystem.domain.usecases.GetAllClients
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::AddClient)
    factoryOf(::GetAllClients)
}