package com.danucdev.stocksystem.domain.usecases

import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.models.ClientModel
import kotlinx.coroutines.flow.Flow

class GetAllClients(private val repository: RepositoryImpl) {
    operator fun invoke(query: String): Flow<List<ClientModel>> {
        return if (query.isBlank()) {
            repository.getAllClients()
        } else {
            repository.getClientsByQuery(query)
        }

    }
}