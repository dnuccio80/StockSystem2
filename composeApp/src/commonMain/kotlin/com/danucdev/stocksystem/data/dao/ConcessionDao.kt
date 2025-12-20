package com.danucdev.stocksystem.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.danucdev.stocksystem.data.entities.ClientEntity
import com.danucdev.stocksystem.data.entities.ConcessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConcessionDao {

    @Insert
    suspend fun addConcession(concession: ConcessionEntity)

    @Query("SELECT * FROM ConcessionEntity order by name ASC")
    fun getAllConcessions(): Flow<List<ConcessionEntity>>
}