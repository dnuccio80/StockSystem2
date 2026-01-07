package com.danucdev.stocksystem.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CourtEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val courtName:String
)
