package com.danucdev.stocksystem.domain.usecases.currentaccounts

import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.models.CurrentAccountModel
import kotlinx.coroutines.flow.Flow

class AddNewCurrentAccount(private val repository: RepositoryImpl) {
    suspend operator fun invoke(currentAccount:CurrentAccountModel):Boolean {
        val alreadyExist: CurrentAccountModel? = repository.isAlreadyCurrentAccount(currentAccount.id)

        return if (alreadyExist != null) {
            true
        } else {
            repository.addCurrentAccount(currentAccount)
            false
        }
    }
}