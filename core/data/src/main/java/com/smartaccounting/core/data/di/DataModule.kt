package com.smartaccounting.core.data.di

import com.smartaccounting.core.data.local.AppDatabase
import com.smartaccounting.core.data.repository.BillRepositoryImpl
import com.smartaccounting.core.data.repository.TagRepositoryImpl
import com.smartaccounting.core.data.repository.UserRepositoryImpl
import com.smartaccounting.core.domain.repository.BillRepository
import com.smartaccounting.core.domain.repository.TagRepository
import com.smartaccounting.core.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { AppDatabase.getDatabase(androidContext()) }
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().billDao() }
    single { get<AppDatabase>().tagDao() }
    
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<BillRepository> { BillRepositoryImpl(get()) }
    single<TagRepository> { TagRepositoryImpl(get()) }
}
