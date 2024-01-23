package com.tailoredapps.bookodyssee_km

import com.tailoredapps.bookodyssee_km.cache.Database
import com.tailoredapps.bookodyssee_km.cache.DatabaseDriverFactory
import com.tailoredapps.bookodyssee_km.db.LocalBook
import com.tailoredapps.bookodyssee_km.db.User


class BookOdysseeSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)

    @Throws(Throwable::class)
    fun getUser(username: String): User? {
        return database.getUser(username)
    }

    @Throws(Throwable::class)
    fun createUser(username: String, password: String) {
        database.createUser(
            User(
                username = username,
                password = password
            )
        )
    }

    @Throws(Throwable::class)
    fun getBook(userId: Long, bookId: String): LocalBook? {
        return database.getBook(
            userId = userId,
            bookId = bookId
        )
    }

    @Throws(Throwable::class)
    fun addBookToReadingList(book: LocalBook) {
        database.addBookToReadingList(book)
    }

    @Throws(Throwable::class)
    fun updateReadingState(readingState: String, userId: Long, bookId: String) {
        database.updateReadingState(
            readingState = readingState,
            userId = userId,
            bookId = bookId
        )
    }

    @Throws(Throwable::class)
    fun removeBookFromReadingList(userId: Long, bookId: String) {
        database.removeBookFromReadingList(
            userId = userId,
            bookId = bookId
        )
    }
}
