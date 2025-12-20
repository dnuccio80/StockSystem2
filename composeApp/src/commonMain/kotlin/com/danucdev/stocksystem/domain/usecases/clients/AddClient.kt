package com.danucdev.stocksystem.domain.usecases.clients

import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.models.ClientModel

class AddClient(private val repository: RepositoryImpl) {
    suspend operator fun invoke(client:ClientModel) = repository.addClient(client)
}