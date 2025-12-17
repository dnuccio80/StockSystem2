package com.danucdev.stocksystem.domain.models

import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.danucdev.stocksystem.data.entities.ClientEntity
import com.danucdev.stocksystem.data.typeconverters.LocalDateTypeConverter
import java.time.LocalDate

data class ClientModel(
    val id:Int = 0,
    val name:String,
    val phone:Double,
    val birthDate: LocalDate
) {
    fun toEntity(): ClientEntity {
        return ClientEntity(
            id = id,
            name = name,
            phone = phone,
            birthDate = birthDate
        )
    }
}
