package com.danucdev.stocksystem.domain

import com.danucdev.stocksystem.domain.usecases.clients.AddClient
import com.danucdev.stocksystem.domain.usecases.clients.DeleteClient
import com.danucdev.stocksystem.domain.usecases.clients.GetAllClients
import com.danucdev.stocksystem.domain.usecases.clients.UpdateClientData
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::AddClient)
    factoryOf(::GetAllClients)
    factoryOf(::DeleteClient)
    factoryOf(::UpdateClientData)
}