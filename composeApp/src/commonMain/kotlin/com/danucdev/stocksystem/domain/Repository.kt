package com.danucdev.stocksystem.domain

import com.danucdev.stocksystem.data.entities.ClientEntity
import com.danucdev.stocksystem.domain.models.ClientModel
import com.danucdev.stocksystem.domain.models.ConcessionModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    // Clients
    fun getAllClients(): Flow<List<ClientModel>>
    suspend fun addClient(client:ClientModel)
    suspend fun deleteClient(clientId:Int)
    suspend fun updateClientData(client: ClientModel)
    fun getClientsByQuery(query:String):Flow<List<ClientModel>>

    // Concessions
    fun getAllConcessions(): Flow<List<ConcessionModel>>
    suspend fun addConcession(concession: ConcessionModel)

}