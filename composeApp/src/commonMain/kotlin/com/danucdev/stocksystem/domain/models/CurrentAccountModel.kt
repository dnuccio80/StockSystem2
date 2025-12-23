package com.danucdev.stocksystem.domain.models

import com.danucdev.stocksystem.data.entities.CurrentAccountEntity

data class CurrentAccountModel(
    val id:Int,
    val clientName:String,
    val amount:String
) {
    fun toEntity(): CurrentAccountEntity {
        return CurrentAccountEntity(
            id = id,
            clientName = clientName,
            amount = amount
        )
    }
}
