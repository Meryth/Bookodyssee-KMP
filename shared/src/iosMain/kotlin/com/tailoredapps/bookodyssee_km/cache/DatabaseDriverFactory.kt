package com.tailoredapps.bookodyssee_km.cache

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver() : SqlDriver {
        return NativeSqliteDriver(BookOdysseeDb.Schema, "book_odyssee.db")
    }
}
