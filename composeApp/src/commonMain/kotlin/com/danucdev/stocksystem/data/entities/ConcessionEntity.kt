package com.danucdev.stocksystem.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danucdev.stocksystem.domain.models.ConcessionModel

@Entity
data class ConcessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val price:String,
    val stock:String?,
    val adviceStock:String?
) {
    fun toDomain():ConcessionModel {
        return ConcessionModel(
            id = id,
            name = name,
            price = price,
            stock = stock,
            adviceStock = adviceStock
        )
    }
}