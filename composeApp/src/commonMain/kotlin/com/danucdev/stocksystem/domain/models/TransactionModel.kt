package com.danucdev.stocksystem.domain.models

import com.danucdev.stocksystem.data.entities.TransactionEntity
import java.time.LocalDate

data class TransactionModel(
    val id:Int = 0,
    val clientId:Int,
    val amount:String,
    val details:String,
    val date:LocalDate
) {
    fun toEntity():TransactionEntity {
        return TransactionEntity(
            transactionId = id,
            clientId = clientId,
            amount = amount,
            details = details,
            date = date
        )
    }
}
