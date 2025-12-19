package com.danucdev.stocksystem.domain.models

import com.danucdev.stocksystem.data.entities.ClientEntity
import java.time.LocalDate

data class ClientModel(
    val id:Int = 0,
    val name:String,
    val phone:String,
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
