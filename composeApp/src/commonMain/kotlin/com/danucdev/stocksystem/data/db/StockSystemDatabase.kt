package com.danucdev.stocksystem.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.danucdev.stocksystem.data.dao.ClientDao
import com.danucdev.stocksystem.data.dao.ConcessionDao
import com.danucdev.stocksystem.data.dao.CurrentAccountDao
import com.danucdev.stocksystem.data.dao.TransactionDao
import com.danucdev.stocksystem.data.entities.ClientEntity
import com.danucdev.stocksystem.data.entities.ConcessionEntity
import com.danucdev.stocksystem.data.entities.CurrentAccountEntity
import com.danucdev.stocksystem.data.entities.TransactionEntity
import com.danucdev.stocksystem.data.typeconverters.LocalDateTypeConverter


const val DATABASE_NAME = "ss_app_database.db" // Siempre con .db

expect object StockSystemCTor : RoomDatabaseConstructor<StockSystemDatabase>

@Database(entities = [ClientEntity::class, ConcessionEntity::class, CurrentAccountEntity::class, TransactionEntity::class], version = 6)
@ConstructedBy(StockSystemCTor::class)
@TypeConverters(LocalDateTypeConverter::class)
abstract class StockSystemDatabase:RoomDatabase() {
    abstract fun clientDao():ClientDao
    abstract fun concessionDao():ConcessionDao
    abstract fun currentAccountDao():CurrentAccountDao
    abstract fun transactionDao():TransactionDao
}

