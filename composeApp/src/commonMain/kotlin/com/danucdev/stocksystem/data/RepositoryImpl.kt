package com.danucdev.stocksystem.data

import com.danucdev.stocksystem.data.db.StockSystemDatabase
import com.danucdev.stocksystem.data.entities.ClientEntity
import com.danucdev.stocksystem.domain.Repository
import com.danucdev.stocksystem.domain.models.ClientModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(private val db: StockSystemDatabase):Repository {
    override fun getAllClients(): Flow<List<ClientModel>> {
        return db.clientDao().getAllClients().map { list ->
            list.map { client ->
                client.toDomain()
            }
        }
    }

    override suspend fun addClient(client: ClientModel) {
        val clientEntity = client.toEntity()

        db.clientDao().addClient(clientEntity)
    }
}