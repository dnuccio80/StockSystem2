package com.danucdev.stocksystem.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danucdev.stocksystem.domain.models.CourtModel

@Entity
data class CourtEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val courtName:String
) {
    fun toDomain():CourtModel = CourtModel(id = id, courtName = courtName)
}
