package com.danucdev.stocksystem.data.di

import com.danucdev.stocksystem.data.db.getDataBase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module {
    return module {
        single { getDataBase() }
    }
}