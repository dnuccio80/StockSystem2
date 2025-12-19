package com.danucdev.stocksystem.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.danucdev.stocksystem.data.entities.ClientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {

    @Insert
    suspend fun addClient(client:ClientEntity)

    @Query("SELECT * FROM CLIENTENTITY order by name ASC")
    fun getAllClients():Flow<List<ClientEntity>>

    @Query("DELETE FROM CLIENTENTITY WHERE id = :clientId")
    suspend fun deleteClient(clientId:Int)

    @Update
    suspend fun updateClientData(client: ClientEntity)


}