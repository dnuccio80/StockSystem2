package com.danucdev.stocksystem.data.dao

import androidx.room.Insert
import androidx.room.Query
import com.danucdev.stocksystem.data.entities.CourtEntity
import kotlinx.coroutines.flow.Flow

interface CourtDao {

    @Query("SELECT * FROM CourtEntity")
    fun getAllCourts(): Flow<List<CourtEntity>>

    @Insert
    suspend fun addNewCourt(court:CourtEntity)

    @Query("DELETE FROM COURTENTITY WHERE id = :courtId")
    suspend fun deleteCourt(courtId:Int)

}