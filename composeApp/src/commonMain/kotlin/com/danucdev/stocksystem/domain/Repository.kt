package com.danucdev.stocksystem.domain

import com.danucdev.stocksystem.data.entities.ClientEntity
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAllClients(): Flow<List<ClientEntity>>
    suspend fun addClient(client:ClientEntity)
}