package com.smartaccounting.app

import android.app.Application
import com.smartaccounting.core.data.di.dataModule
import com.smartaccounting.core.domain.di.domainModule
import com.smartaccounting.core.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SmartAccountingApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SmartAccountingApp)
            modules(
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}
