package com.tailoredapps.bookodyssee_km.android.ui.book

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val bookModule = module {
    viewModelOf(::BookViewModel)
}