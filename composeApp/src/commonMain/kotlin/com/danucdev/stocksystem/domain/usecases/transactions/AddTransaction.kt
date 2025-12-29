package com.danucdev.stocksystem.domain.usecases.transactions

import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.models.TransactionModel

class AddTransaction(private val repository: RepositoryImpl) {
    suspend operator fun invoke(transaction: TransactionModel) = repository.addTransaction(transaction)
}