package com.danucdev.stocksystem.domain.usecases.currentaccounts

import com.danucdev.stocksystem.data.RepositoryImpl

class GetCurrentAccountDetails(private val repository: RepositoryImpl) {
    suspend operator fun invoke(accountId:Int) = repository.getSingleCurrentAccountById(accountId)
}