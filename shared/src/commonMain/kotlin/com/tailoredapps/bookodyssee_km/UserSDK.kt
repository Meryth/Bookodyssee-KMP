package com.tailoredapps.bookodyssee_km

import com.tailoredapps.bookodyssee_km.cache.Database
import com.tailoredapps.bookodyssee_km.cache.DatabaseDriverFactory
import com.tailoredapps.bookodyssee_km.db.User


class UserSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)

    fun getUser(username: String): User? {
        return database.getUser(username)
    }

    fun createUser(username: String, password: String) {
        database.createUser(
            User(
                username = username,
                password = password
            )
        )
    }

}
