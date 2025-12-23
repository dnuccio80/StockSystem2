package com.danucdev.stocksystem.domain.usecases.concessions

import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.models.ConcessionModel
import kotlinx.coroutines.flow.Flow

class GetConcessions(private val repository: RepositoryImpl) {
    operator fun invoke(query:String):Flow<List<ConcessionModel>> {
        return if(query.isBlank()) {
            repository.getAllConcessions()
        } else {
            repository.getConcessionByQuery(query)
        }
    }
}