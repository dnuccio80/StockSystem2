package com.danucdev.stocksystem.domain

import com.danucdev.stocksystem.data.entities.ClientEntity
import com.danucdev.stocksystem.domain.models.ClientModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAllClients(): Flow<List<ClientModel>>
    suspend fun addClient(client:ClientModel)
}