package com.tailoredapps.bookodyssee_km

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataRepo {
    private val platform: Platform = getPlatform()
    private val bookComponent = BookComponent()

    @NativeCoroutines
    fun searchBooks(searchTerm: String): Flow<RemoteBookList> = flow {
        emit(bookComponent.findBookBySearchTerm(searchTerm))
    }
}