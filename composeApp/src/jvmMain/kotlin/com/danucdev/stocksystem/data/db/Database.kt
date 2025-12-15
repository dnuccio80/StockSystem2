package com.danucdev.stocksystem.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danucdev.stocksystem.data.dao.ClientDao
import com.danucdev.stocksystem.data.entities.ClientEntity
import com.danucdev.stocksystem.data.typeconverters.LocalDateTypeConverter

@Database(entities = [ClientEntity::class], version = 1)
@TypeConverters(LocalDateTypeConverter::class)
abstract class Database:RoomDatabase() {
    abstract val clientDao:ClientDao
}