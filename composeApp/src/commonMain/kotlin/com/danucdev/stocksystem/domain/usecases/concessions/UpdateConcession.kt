package com.danucdev.stocksystem.domain.usecases.concessions

import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.models.ConcessionModel

class UpdateConcession(private val repository: RepositoryImpl) {
    suspend operator fun invoke(concession: ConcessionModel) = repository.updateConcession(concession)
}