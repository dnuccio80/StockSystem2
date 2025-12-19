package com.danucdev.stocksystem.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.danucdev.stocksystem.data.typeconverters.LocalDateTypeConverter
import com.danucdev.stocksystem.domain.models.ClientModel
import java.time.LocalDate

@Entity
data class ClientEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val phone:String,
    @TypeConverters(LocalDateTypeConverter::class) val birthDate: LocalDate
) {
    fun toDomain():ClientModel{
        return ClientModel(
            id = id,
            name = name,
            phone = phone,
            birthDate = birthDate
        )
    }
}
