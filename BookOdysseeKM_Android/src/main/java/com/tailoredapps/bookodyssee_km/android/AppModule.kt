package com.tailoredapps.bookodyssee_km.android

import com.tailoredapps.bookodyssee_km.BookOdysseeSDK
import com.tailoredapps.bookodyssee_km.SettingsInstance
import com.tailoredapps.bookodyssee_km.android.ui.book.bookModule
import com.tailoredapps.bookodyssee_km.android.ui.finished.finishedModule
import com.tailoredapps.bookodyssee_km.android.ui.home.homeModule
import com.tailoredapps.bookodyssee_km.android.ui.login.loginModule
import com.tailoredapps.bookodyssee_km.android.ui.registration.registrationModule
import com.tailoredapps.bookodyssee_km.android.ui.search.searchModule
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
    homeModule,
    finishedModule,
    searchModule,
    bookModule,
)
