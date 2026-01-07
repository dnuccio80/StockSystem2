package com.danucdev.stocksystem.domain

import com.danucdev.stocksystem.data.entities.TransactionEntity
import com.danucdev.stocksystem.domain.models.ClientModel
import com.danucdev.stocksystem.domain.models.ConcessionModel
import com.danucdev.stocksystem.domain.models.CourtModel
import com.danucdev.stocksystem.domain.models.CurrentAccountModel
import com.danucdev.stocksystem.domain.models.TransactionModel
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
    suspend fun updateConcession(concession: ConcessionModel)
    suspend fun deleteConcession(concessionId:Int)
    fun getConcessionByQuery(query: String):Flow<List<ConcessionModel>>

    // Current Accounts
    fun getAllCurrentAccounts(): Flow<List<CurrentAccountModel>>
    suspend fun addCurrentAccount(currentAccount: CurrentAccountModel)
    suspend fun updateCurrentAccount(currentAccount: CurrentAccountModel)
    suspend fun deleteCurrentAccount(currentAccountId: Int)
    fun getCurrentAccountsByQuery(query:String): Flow<List<CurrentAccountModel>>
    suspend fun getSingleCurrentAccountById(accountId:Int):CurrentAccountModel?

    // Transactions
    fun getTransactionsByClientId(clientId:Int): Flow<List<TransactionModel>?>
    suspend fun addTransaction(transaction: TransactionModel)
    suspend fun deleteTransaction(transaction: TransactionModel)
    suspend fun updateTransaction(transaction: TransactionModel)
    suspend fun deleteAllTransactionsByClientId(clientId: Int)

    // Courts
    fun getAllCourts():Flow<List<CourtModel>>
    suspend fun addNewCourt(court:CourtModel)
    suspend fun deleteCourt(courtId:Int)

}