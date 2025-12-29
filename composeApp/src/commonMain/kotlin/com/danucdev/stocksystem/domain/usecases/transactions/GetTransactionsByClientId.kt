package com.danucdev.stocksystem.domain.usecases.transactions

import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.models.TransactionModel
import kotlinx.coroutines.flow.Flow

class GetTransactionsByClientId(private val repository: RepositoryImpl) {
    operator fun invoke(clientId:Int):Flow<List<TransactionModel>?> = repository.getTransactionsByClientId(clientId)
}