package com.danucdev.stocksystem.domain.usecases.transactions

import com.danucdev.stocksystem.data.RepositoryImpl

class DeleteAllTransactionsByClientId(private val repository: RepositoryImpl) {
    suspend operator fun invoke(clientId:Int) = repository.deleteAllTransactionsByClientId(clientId)
}