package com.danucdev.stocksystem.data

import com.danucdev.stocksystem.data.db.StockSystemDatabase
import com.danucdev.stocksystem.data.entities.ClientEntity
import com.danucdev.stocksystem.domain.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val db: StockSystemDatabase):Repository {
    override fun getAllClients(): Flow<List<ClientEntity>> {
        return db.clientDao().getAllClients()
    }

    override suspend fun addClient(client: ClientEntity) {
        db.clientDao().addClient(client)
    }
}