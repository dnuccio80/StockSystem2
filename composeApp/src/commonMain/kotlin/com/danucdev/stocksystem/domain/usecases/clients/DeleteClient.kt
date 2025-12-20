package com.danucdev.stocksystem.domain.usecases.clients

import com.danucdev.stocksystem.data.RepositoryImpl

class DeleteClient(private val repository: RepositoryImpl) {
    suspend operator fun invoke(clientId:Int) = repository.deleteClient(clientId)
}