package com.danucdev.stocksystem.data

import com.danucdev.stocksystem.data.db.StockSystemDatabase
import com.danucdev.stocksystem.domain.Repository
import com.danucdev.stocksystem.domain.models.ClientModel
import com.danucdev.stocksystem.domain.models.ConcessionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(private val db: StockSystemDatabase) : Repository {

    // Clients

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

    override suspend fun deleteClient(clientId: Int) {
        db.clientDao().deleteClient(clientId)
    }

    override suspend fun updateClientData(client: ClientModel) {
        db.clientDao().updateClientData(client.toEntity())
    }

    override fun getClientsByQuery(query: String): Flow<List<ClientModel>> {
        return db.clientDao().getClientsByQuery(query).map { list ->
            list.map { client -> client.toDomain() }
        }
    }

    // Concessions

    override fun getAllConcessions(): Flow<List<ConcessionModel>> {
        return db.concessionDao().getAllConcessions().map { list ->
            list.map { concession -> concession.toDomain() }
        }
    }

    override suspend fun addConcession(concession: ConcessionModel) {
        db.concessionDao().addConcession(concession.toEntity())
    }

    override suspend fun updateConcession(concession: ConcessionModel) {
        db.concessionDao().updateConcession(concession.toEntity())
    }

    override suspend fun deleteConcession(concessionId: Int) {
        db.concessionDao().deleteConcession(concessionId)
    }

    override fun getConcessionByQuery(query: String): Flow<List<ConcessionModel>> {
        return db.concessionDao().getConcessionByQuery(query).map { list ->
            list.map { concession -> concession.toDomain() }
        }
    }
}