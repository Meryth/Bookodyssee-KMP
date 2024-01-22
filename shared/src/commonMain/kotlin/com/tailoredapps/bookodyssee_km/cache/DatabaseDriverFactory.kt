package com.tailoredapps.bookodyssee_km.cache

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun createDriver() : SqlDriver
}