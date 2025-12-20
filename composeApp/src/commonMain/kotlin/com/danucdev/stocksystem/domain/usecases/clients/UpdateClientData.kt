package com.danucdev.stocksystem.domain.usecases.clients

import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.models.ClientModel

class UpdateClientData(private val repository: RepositoryImpl) {
    suspend operator fun invoke(client:ClientModel) = repository.updateClientData(client)
}