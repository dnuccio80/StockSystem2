package com.danucdev.stocksystem.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.danucdev.stocksystem.domain.models.CurrentAccountModel

@Entity(
    foreignKeys = [ForeignKey(
        entity = ClientEntity::class,
        parentColumns = ["id"],
        childColumns = ["clientId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["clientId"])]
)
data class CurrentAccountEntity(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val clientId:Int,
    val clientName:String,
    val amount:String
) {
    fun toDomain(): CurrentAccountModel {
        return CurrentAccountModel(
            id = id,
            clientId = clientId,
            clientName = clientName,
            amount = amount
        )
    }
}
