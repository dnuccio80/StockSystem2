package com.danucdev.stocksystem.domain.usecases.currentaccounts

import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.models.CurrentAccountModel
import kotlinx.coroutines.flow.Flow

class GetCurrentAccounts (private val repository: RepositoryImpl) {
    operator fun invoke(query:String): Flow<List<CurrentAccountModel>> {
        return if(query.isBlank()) {
            repository.getAllCurrentAccounts()
        } else {
            repository.getCurrentAccountsByQuery(query)
        }
    }
}