package com.tailoredapps.bookodyssee_km.android

import com.tailoredapps.bookodyssee_km.BookOdysseeSDK
import com.tailoredapps.bookodyssee_km.SettingsInstance
import com.tailoredapps.bookodyssee_km.android.book.bookModule
import com.tailoredapps.bookodyssee_km.android.login.loginModule
import com.tailoredapps.bookodyssee_km.android.registration.registrationModule
import com.tailoredapps.bookodyssee_km.android.search.searchModule
import com.tailoredapps.bookodyssee_km.cache.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val appModule = module {
    single { BookOdysseeSDK(DatabaseDriverFactory(androidContext())) }
    single {
        val context = androidContext()
        SettingsInstance(context).createSettings()
    }
}


internal val appModules = listOf(
    appModule,
    loginModule,
    registrationModule,
    searchModule,
    bookModule,
)
