package com.tailoredapps.bookodyssee_km

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataRepo {
    private val platform: Platform = getPlatform()
    private val bookComponent = BookComponent()

    fun searchBooks(): Flow<RemoteBookList> = flow {
        emit(bookComponent.findBookBySearchTerm("1984"))
    }
}