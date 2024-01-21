package com.tailoredapps.bookodyssee_km.android

import com.tailoredapps.bookodyssee_km.android.login.loginModule
import com.tailoredapps.bookodyssee_km.android.search.searchModule
import org.koin.dsl.module

internal val appModule = module {
    //TODO: will probably need for shared prefs if there is no alternative from KMP
}


internal val appModules = listOf(
    appModule,
    loginModule,
    searchModule
)
