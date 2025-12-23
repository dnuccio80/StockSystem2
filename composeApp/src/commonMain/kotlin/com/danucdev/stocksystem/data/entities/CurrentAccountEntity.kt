package com.danucdev.stocksystem.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danucdev.stocksystem.domain.models.CurrentAccountModel

@Entity
data class CurrentAccountEntity(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val clientName:String,
    val amount:String
) {
    fun toDomain(): CurrentAccountModel {
        return CurrentAccountModel(
            id = id,
            clientName = clientName,
            amount = amount
        )
    }
}
