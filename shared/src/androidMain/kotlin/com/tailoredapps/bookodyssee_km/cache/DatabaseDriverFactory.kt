package com.tailoredapps.bookodyssee_km.cache

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver() : SqlDriver {
        return AndroidSqliteDriver(BookOdysseeDb.Schema, context, "book_odyssee.db")
    }
}
