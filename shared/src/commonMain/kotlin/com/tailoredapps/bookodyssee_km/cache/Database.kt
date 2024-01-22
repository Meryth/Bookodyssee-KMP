package com.tailoredapps.bookodyssee_km.cache

import com.tailoredapps.bookodyssee_km.db.User

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = BookOdysseeDb(databaseDriverFactory.createDriver())
    private val dbQuery = database.bookOdysseeQueries

    internal fun getUser(username: String): User? {
        val queryResult = dbQuery.selectUser(username = username).executeAsOneOrNull()
        return if (queryResult != null) {
            User(
                username = queryResult.username.orEmpty(),
                password = queryResult.password.orEmpty()
            )
        } else {
            null
        }
    }

    internal fun createUser(user: User) {
        dbQuery.transaction {
            insertUser(user)
        }
    }

    private fun insertUser(user: User) {
        dbQuery.insertUser(
            username = user.username,
            password = user.password
        )
    }

}
