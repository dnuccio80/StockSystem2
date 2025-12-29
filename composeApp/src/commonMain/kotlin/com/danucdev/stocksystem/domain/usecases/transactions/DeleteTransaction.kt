package com.danucdev.stocksystem.domain.usecases.transactions

import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.models.TransactionModel

class DeleteTransaction(private val repository: RepositoryImpl) {
    suspend operator fun invoke(transaction: TransactionModel) = repository.deleteTransaction(transaction)
}