package com.danucdev.stocksystem.domain.usecases.currentaccounts

import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.models.CurrentAccountModel

class AddNewCurrentAccount(private val repository: RepositoryImpl) {
    suspend operator fun invoke(currentAccount:CurrentAccountModel):Boolean {
        val alreadyExist: CurrentAccountModel? = repository.getSingleCurrentAccountById(currentAccount.id)

        return if (alreadyExist != null) {
            true
        } else {
            repository.addCurrentAccount(currentAccount)
            false
        }
    }
}