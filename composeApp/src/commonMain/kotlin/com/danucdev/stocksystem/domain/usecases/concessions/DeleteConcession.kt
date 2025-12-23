package com.danucdev.stocksystem.domain.usecases.concessions

import com.danucdev.stocksystem.data.RepositoryImpl

class DeleteConcession(private val repository: RepositoryImpl) {
    suspend operator fun invoke(concessionId:Int) = repository.deleteConcession(concessionId)
}