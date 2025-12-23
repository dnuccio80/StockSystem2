package com.danucdev.stocksystem.domain

import com.danucdev.stocksystem.domain.usecases.clients.AddClient
import com.danucdev.stocksystem.domain.usecases.clients.DeleteClient
import com.danucdev.stocksystem.domain.usecases.clients.GetAllClients
import com.danucdev.stocksystem.domain.usecases.clients.UpdateClientData
import com.danucdev.stocksystem.domain.usecases.concessions.AddConcession
import com.danucdev.stocksystem.domain.usecases.concessions.DeleteConcession
import com.danucdev.stocksystem.domain.usecases.concessions.GetConcessions
import com.danucdev.stocksystem.domain.usecases.concessions.UpdateConcession
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    // Clients
    factoryOf(::AddClient)
    factoryOf(::GetAllClients)
    factoryOf(::DeleteClient)
    factoryOf(::UpdateClientData)

    // Concessions
    factoryOf(::AddConcession)
    factoryOf(::DeleteConcession)
    factoryOf(::GetConcessions)
    factoryOf(::UpdateConcession)
}