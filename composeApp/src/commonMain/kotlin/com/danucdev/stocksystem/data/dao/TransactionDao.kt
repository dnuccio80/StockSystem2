package com.danucdev.stocksystem.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.danucdev.stocksystem.data.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM TransactionEntity WHERE clientId = :clientId")
    fun getTransactionsByClientId(clientId:Int): Flow<List<TransactionEntity>?>

    @Insert
    suspend fun addTransaction(transaction:TransactionEntity)

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM TransactionEntity WHERE clientId = :clientId")
    suspend fun deleteTransactionsByClientId(clientId: Int)

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

}