package com.danucdev.stocksystem.domain.models

import com.danucdev.stocksystem.data.entities.CourtEntity

data class CourtModel(
    val id:Int = 0,
    val courtName:String
) {
    fun toEntity():CourtEntity = CourtEntity(id = id, courtName = courtName)
}
