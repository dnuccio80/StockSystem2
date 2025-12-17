package com.danucdev.stocksystem.data.di

import com.danucdev.stocksystem.data.RepositoryImpl
import com.danucdev.stocksystem.domain.Repository
import org.koin.dsl.module


val dataModule = module {
    factory<Repository> { RepositoryImpl(get()) }
}