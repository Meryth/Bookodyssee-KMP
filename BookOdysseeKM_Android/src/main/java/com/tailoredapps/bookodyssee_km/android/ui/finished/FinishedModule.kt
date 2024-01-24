package com.tailoredapps.bookodyssee_km.android.ui.finished

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val finishedModule = module {
    viewModelOf(::FinishedViewModel)
}
