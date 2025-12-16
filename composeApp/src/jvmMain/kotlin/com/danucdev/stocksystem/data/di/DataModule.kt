package com.danucdev.stocksystem.data.di

import com.danucdev.stocksystem.data.db.getDataBase
import org.koin.dsl.module


val dataModule = module {
    single { getDataBase() }
}