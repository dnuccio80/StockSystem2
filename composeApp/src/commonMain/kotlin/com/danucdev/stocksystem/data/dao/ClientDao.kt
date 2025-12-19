package com.danucdev.stocksystem.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.danucdev.stocksystem.data.entities.ClientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {

    @Insert
    suspend fun addClient(client:ClientEntity)

    @Query("SELECT * FROM CLIENTENTITY order by name ASC")
    fun getAllClients():Flow<List<ClientEntity>>


}