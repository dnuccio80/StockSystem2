package com.danucdev.stocksystem.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.danucdev.stocksystem.data.entities.ConcessionEntity
import com.danucdev.stocksystem.data.entities.CurrentAccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentAccountDao {
    @Insert
    suspend fun addCurrentAccount(currentAccount: CurrentAccountEntity)

    @Query("SELECT * FROM CurrentAccountEntity order by clientName ASC")
    fun getAllCurrentAccounts(): Flow<List<CurrentAccountEntity>>

    @Update
    suspend fun updateCurrentAccount(currentAccount: CurrentAccountEntity)

    @Query("DELETE FROM CurrentAccountEntity WHERE id = :accountId")
    suspend fun deleteCurrentAccount(accountId:Int)

    @Query("SELECT * FROM CurrentAccountEntity WHERE clientName LIKE '%' || :query || '%' ")
    fun getCurrentAccountByQuery(query:String):Flow<List<CurrentAccountEntity>>
}