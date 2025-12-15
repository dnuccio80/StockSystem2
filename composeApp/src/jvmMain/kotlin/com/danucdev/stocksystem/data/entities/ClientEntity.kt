package com.danucdev.stocksystem.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.danucdev.stocksystem.data.typeconverters.LocalDateTypeConverter
import java.time.LocalDate

@Entity
data class ClientEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name:String,
    val phone:Double,
    @TypeConverters(LocalDateTypeConverter::class) val birthDate: LocalDate
)
