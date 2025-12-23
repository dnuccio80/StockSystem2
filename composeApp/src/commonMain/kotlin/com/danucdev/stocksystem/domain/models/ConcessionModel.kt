package com.danucdev.stocksystem.domain.models

import com.danucdev.stocksystem.data.entities.ConcessionEntity

data class ConcessionModel(
    val id:Int = 0,
    val name:String,
    val price:String,
    val stock:String?,
    val adviceStock:String?,
){
    fun toEntity():ConcessionEntity {
        return ConcessionEntity(
            id = id,
            name = name,
            price = price,
            stock = stock,
            adviceStock = adviceStock
        )
    }
}