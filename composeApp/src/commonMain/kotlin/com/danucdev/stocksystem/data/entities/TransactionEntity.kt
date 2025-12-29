package com.danucdev.stocksystem.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.danucdev.stocksystem.data.typeconverters.LocalDateTypeConverter
import com.danucdev.stocksystem.domain.models.TransactionModel
import java.time.LocalDate

@Entity
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val transactionId:Int,
    val clientId:Int,
    val amount:String,
    val details:String,
    @TypeConverters(LocalDateTypeConverter::class) val date: LocalDate
) {
    fun toDomain():TransactionModel {
        return TransactionModel(
            id = transactionId,
            clientId = clientId,
            amount = amount,
            details = details,
            date = date
        )
    }
}
